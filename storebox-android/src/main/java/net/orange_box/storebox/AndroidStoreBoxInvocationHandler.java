package net.orange_box.storebox;

import android.content.SharedPreferences;

import net.orange_box.storebox.engines.SharedPreferencesEngine;
import net.orange_box.storebox.enums.SaveMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AndroidStoreBoxInvocationHandler extends StoreBoxInvocationHandler {

    public AndroidStoreBoxInvocationHandler(StoreEngine engine, Class cls, SaveMode saveMode) {
        super(engine, cls, saveMode);
    }

    @Override
    public Object forwardMethod(StoreEngine engine, Method method, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        SharedPreferencesEngine mEngine = (SharedPreferencesEngine) engine;
        SharedPreferences prefs = mEngine.getPrefs();
        SharedPreferences.Editor editor = prefs.edit();

        // can we forward the method to the SharedPreferences?
        try {
            final Method prefsMethod = prefs.getClass().getDeclaredMethod(
                    method.getName(),
                    method.getParameterTypes());

            return prefsMethod.invoke(prefs, args);
        } catch (NoSuchMethodException e) {
            throw e;
        } catch (InvocationTargetException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        } finally {
            // can we forward the method to the SharedPreferences.Editor?
            try {
                final Method editorMethod = editor.getClass().getDeclaredMethod(
                        method.getName(),
                        method.getParameterTypes());

                return editorMethod.invoke(editor, args);
            } catch (NoSuchMethodException e) {
                throw e;
            } catch (InvocationTargetException e) {
                throw e;
            } catch (IllegalAccessException e) {
                throw e;
            } finally {
                // Parent forward
                return super.forwardMethod(engine, method, args);
            }
        }
    }

}
