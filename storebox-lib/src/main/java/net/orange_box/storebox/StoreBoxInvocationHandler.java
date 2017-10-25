/*
 * Copyright 2015 Martin Bella
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.orange_box.storebox;

import net.orange_box.storebox.adapters.StoreBoxTypeAdapter;
import net.orange_box.storebox.annotations.method.ClearMethod;
import net.orange_box.storebox.annotations.method.DefaultValue;
import net.orange_box.storebox.annotations.method.KeyByResource;
import net.orange_box.storebox.annotations.method.KeyByString;
import net.orange_box.storebox.annotations.method.RemoveMethod;
import net.orange_box.storebox.annotations.method.TypeAdapter;
import net.orange_box.storebox.annotations.method.RegisterChangeListenerMethod;
import net.orange_box.storebox.annotations.method.UnregisterChangeListenerMethod;
import net.orange_box.storebox.annotations.option.SaveOption;
import net.orange_box.storebox.enums.SaveMode;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;
import net.orange_box.storebox.handlers.MethodHandler;
import net.orange_box.storebox.utils.MethodUtils;
import net.orange_box.storebox.utils.PreferenceUtils;
import net.orange_box.storebox.utils.TypeUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Locale;

/**
 * This is where the magic happens...
 */
class StoreBoxInvocationHandler implements InvocationHandler {

    private static final Method OBJECT_EQUALS =
            MethodUtils.getObjectMethod("equals", Object.class);
    private static final Method OBJECT_HASHCODE =
            MethodUtils.getObjectMethod("hashCode");
    private static final Method OBJECT_TOSTRING =
            MethodUtils.getObjectMethod("toString");
    
    private final StoreEngine engine;
    private final Class cls;

    private final SaveMode saveMode;
    
    private final MethodHandler mChangesHandler;
    
    private int hashCode;
    
    public StoreBoxInvocationHandler(
            StoreEngine engine,
            Class cls,
            SaveMode saveMode) {
        
        this.engine = engine;
        this.cls = cls;
        this.saveMode = saveMode;
        
        mChangesHandler = new ChangeListenerMethodHandler(engine);
    }
    
    @Override
    public Object invoke(
            Object proxy, Method method, Object... args) throws Throwable {
        
        /*
         * Find the key for the preference from the method's annotation, or
         * whether it's a remove method, else we try to forward the method to
         * the SharedPreferences or Editor implementations.
         */
        final String key;
        final boolean isRemove;
        final boolean isClear;
        final boolean isChange;
        if (method.isAnnotationPresent(KeyByString.class)) {
            key = method.getAnnotation(KeyByString.class).value();
            
            isRemove = method.isAnnotationPresent(RemoveMethod.class);
            isClear = false;
            isChange = MethodUtils.areAnyAnnotationsPresent(
                    method,
                    RegisterChangeListenerMethod.class,
                    UnregisterChangeListenerMethod.class);
        } else if (method.isAnnotationPresent(KeyByResource.class)) {
            key = (String) engine.getResourceString(
                    method.getAnnotation(KeyByResource.class).value());
            
            isRemove = method.isAnnotationPresent(RemoveMethod.class);
            isClear = false;
            isChange = MethodUtils.areAnyAnnotationsPresent(
                    method,
                    RegisterChangeListenerMethod.class,
                    UnregisterChangeListenerMethod.class);
        } else if (method.isAnnotationPresent(RemoveMethod.class)) {
            key = MethodUtils.getKeyForRemove(engine, args);
            
            isRemove = true;
            isClear = false;
            isChange = false;
        } else if (method.isAnnotationPresent(ClearMethod.class)) {
            key = null;
            
            isRemove = false;
            isClear = true;
            isChange = false;
        } else {
            // handle Object's equals/hashCode/toString
            if (method.equals(OBJECT_EQUALS)) {
                return internalEquals(proxy, args[0]);
            } else if (method.equals(OBJECT_HASHCODE)) {
                return internalHashCode();
            } else if (method.equals(OBJECT_TOSTRING)) {
                return toString();
            }

            try {
                return forwardMethod(engine, method, args);
            } catch (NoSuchMethodException e) {
                // NOP
            } catch (InvocationTargetException e) {
                throw e;
            } catch (IllegalAccessException e) {
                throw e;
            }

            // fail fast, rather than ignoring the method invocation
            throw new UnsupportedOperationException(String.format(
                    Locale.ENGLISH,
                    "Failed to invoke %1$s method, " +
                            "perhaps the %2$s or %3$s annotation is missing?",
                    method.getName(),
                    KeyByString.class.getSimpleName(),
                    KeyByResource.class.getSimpleName()));
        }

        // method-level strategy > class-level strategy
        final SaveMode mode;
        if (method.isAnnotationPresent(SaveOption.class)) {
            mode = method.getAnnotation(SaveOption.class).value();
        } else {
            mode = saveMode;
        }

        /*
         * Find out based on the method return type whether it's a get or set
         * operation. We could provide a further annotation for get/set methods,
         * but we can infer this reasonably easily.
         */
        final Class<?> returnType = method.getReturnType();
        if (isRemove) {
            engine.remove(key, mode);
        } else if (isClear) {
            engine.clear(mode);
        } else if (isChange) {
            return mChangesHandler.handleInvocation(key, proxy, method, args);
        } else if (
                returnType == Void.TYPE
                        || returnType == method.getDeclaringClass()
                        || returnType == cls) {
            setMethod(method, key, mode, args);

        } else {
            try {
                return getMethod(method, key, args);
            } catch (UnsupportedOperationException e) {
                throw e;
            } catch (RuntimeException e) {
                setMethod(method, key, mode, args);
            }
        }
        //PreferenceUtils.saveChanges(engine, mode);
        return chainingMethod(engine, method, returnType, cls, proxy);
    }

    /*
     * Get.
     *
     * We wrap any primitive types to their boxed equivalents as this
     * makes further operations a bit nicer.
     */
    private Object getMethod(Method method, String key, Object[] args) {
        final StoreBoxTypeAdapter adapter = TypeUtils.getTypeAdapter(
                TypeUtils.wrapToBoxedType(method.getReturnType()),
                method.getAnnotation(TypeAdapter.class));

        final Object defValue = getDefaultValueArg(
                method,
                args);

        final Object value = PreferenceUtils.getValue(
                engine,
                key,
                adapter.getStoreType(),
                (defValue == null)
                        ? adapter.getDefaultValue()
                        : adapter.adaptForPreferences(defValue));

        return adapter.adaptFromPreferences(value);
    }

    /*
     * Set.
     *
     * Argument types are boxed for us, so we only need to check one
     * variant and we also need to find out what type to store the
     * value under,
     */
    private void setMethod(Method method, String key, SaveMode mode, Object[] args) {
        final StoreBoxTypeAdapter adapter = TypeUtils.getTypeAdapter(
                MethodUtils.getValueParameterType(method),
                method.getAnnotation(TypeAdapter.class));
        final Object value = adapter.adaptForPreferences(
                MethodUtils.getValueArg(args));

        PreferenceUtils.putValue(
                engine, key, adapter.getStoreType(), value, mode);
    }

    public Object forwardMethod(StoreEngine engine, Method method, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // can we forward the method to the Engine?
        final Method prefsMethod = cls.getDeclaredMethod(
                method.getName(),
                method.getParameterTypes());

        return prefsMethod.invoke(engine, args);
    }

    public Object chainingMethod(StoreEngine engine, Method method, Class<?> returnType, Class cls, Object proxy) {
        // allow chaining if appropriate
        if (returnType == method.getDeclaringClass()) {
            return proxy;
        } else if (returnType == cls) {
            return engine;
        } else {
            return null;
        }
    }

    private boolean internalEquals(Object us, Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != us.getClass()) {
            return false;
        }

        final InvocationHandler otherHandler =
                Proxy.getInvocationHandler(other);
        return  (otherHandler instanceof StoreBoxInvocationHandler)
                && (us == other);

    }
    
    private int internalHashCode() {
        if (hashCode == 0) {
            hashCode = Arrays.hashCode(new Object[] {
                    engine, saveMode});
        }
        
        return hashCode;
    }
    
    private Object getDefaultValueArg(
            Method method,
            Object... args) {
        
        Object result = null;
        final Class<?> type = TypeUtils.wrapToBoxedType(method.getReturnType());
        
        // parameter default > method-level default
        if (args != null && args.length > 0) {
            result = args[0];
        }
        if (result == null && method.isAnnotationPresent(DefaultValue.class)) {
            final int resourceId =
                    method.getAnnotation(DefaultValue.class).value();
            String defType =
                    engine.getResourceTypeName(resourceId);
            if (type.getSimpleName().toLowerCase().equals(defType)) {
                if (type == Boolean.class) {
                    result = engine.getResourceBoolean(resourceId);
                } else if (type == Float.class) {
                    result = engine.getResourceFloat(resourceId);
                } else if (type == Integer.class) {
                    result = engine.getResourceInt(resourceId);
                } else if (type == Long.class) {
                    result = engine.getResourceLong(resourceId);
                } else if (type == String.class) {
                    String value = engine.getResourceString(resourceId);
                    if (value == null) {
                        result = new Object(); // we'll fail later
                    } else {
                        result = value;
                    }
                } else {
                    throw new UnsupportedOperationException(
                            type.getName() + " not supported as a resource default");
                }
            } else {
                result = new Object(); // we'll fail later
            }
        }
        
        // default was not provided so let's see how we should create it
        if (result == null) {
            return null;
        } else {
            if (!type.isAssignableFrom(result.getClass())) {
                throw new UnsupportedOperationException(String.format(
                        Locale.ENGLISH,
                        "Return type %1$s and default value type %2$s not the same",
                        result.getClass().getName(),
                        type.getName()));
            } else {
                return result;
            }
        }
    }
}