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
    public Object forwardMethod(StoreEngine engine, Method method, Object... args) {

        SharedPreferencesEngine mEngine = (SharedPreferencesEngine) engine;
        SharedPreferences prefs = mEngine.getPrefs();
        SharedPreferences.Editor editor = prefs.edit();

        // can we forward the method to the Engine?
        try {
            final Method prefsMethod = prefs.getClass().getDeclaredMethod(
                    method.getName(),
                    method.getParameterTypes());

            return prefsMethod.invoke(prefs, args);
        } catch (NoSuchMethodException e) {
            // NOP
        } catch (InvocationTargetException e) {
            // NOP
        } catch (IllegalAccessException e) {
            // NOP
        }

        // can we forward the method to the Editor?
        try {
            final Method editorMethod = editor.getClass().getDeclaredMethod(
                    method.getName(),
                    method.getParameterTypes());

            return editorMethod.invoke(editor, args);
        } catch (NoSuchMethodException e) {
            // NOP
        } catch (InvocationTargetException e) {
            // NOP
        } catch (IllegalAccessException e) {
            // NOP
        }

        return super.forwardMethod(engine, method, args);
    }
}
