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

import net.orange_box.storebox.annotations.option.SaveOption;
import net.orange_box.storebox.enums.SaveMode;

import java.lang.reflect.Proxy;

/**
 * StoreBox for Java SE
 *
 * Creates a no-thrills instance of the supplied interface, by reading any
 * options provided through interface-level annotations.
 * <p>
 * Requires to define your own typed key-value storage implementing {@link StoreEngine}
 * <p>
 * If you'd like to provide options dynamically at run-time, take a look at
 * {@link Builder}.
 */
public class StoreBoxSE {

    /**
     * @param engine - storage engine
     * @param cls - the interface class which should be instantiated
     * @return new instance of class {@code cls} using {@code context}
     */
    public static <T> T create(StoreEngine engine, Class<T> cls) {
        return new Builder<>(engine, cls).build();
    }

    protected StoreBoxSE() {}

    /**
     * Can be used to provide a customised instance of the supplied interface,
     * by setting custom options through builder methods.
     * 
     * @param <T>
     */
    public static class Builder<T> {

        protected final StoreEngine engine;
        protected final Class<T> cls;

        protected SaveMode saveMode = SaveMode.APPLY;

        public Builder(StoreEngine engine, Class<T> cls) {
            this.engine = engine;
            this.cls = cls;

            readAnnotations();
        }

        public Builder saveMode(SaveMode value) {
            saveMode = value;
            return this;
        }

        /**
         * @return new instance of class {@code cls} using {@code context}
         */
        @SuppressWarnings("unchecked")
        public T build() {
            validate();
            
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    new Class[]{cls},
                    new StoreBoxInvocationHandler(
                            engine,
                            cls,
                            saveMode));
        }
        
        protected void readAnnotations() {
            // save option
            if (cls.isAnnotationPresent(SaveOption.class)) {
                saveMode(cls.getAnnotation(SaveOption.class).value());
            }
        }
        
        protected void validate() {
            if (engine == null) {
                throw new IllegalArgumentException(
                        "Engine cannot be null");
            }
            if (cls == null) {
                throw new IllegalArgumentException(
                        "Class cannot be null");
            } else if (!cls.isInterface()) {
                throw new IllegalArgumentException(
                        "Class needs to be an interface");
            }
        }
    }

}
