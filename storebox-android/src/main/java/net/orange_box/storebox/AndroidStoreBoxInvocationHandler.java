package net.orange_box.storebox;

import android.content.SharedPreferences;

import net.orange_box.storebox.engines.SharedPreferencesEngine;
import net.orange_box.storebox.enums.SaveMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AndroidStoreBoxInvocationHandler extends StoreBoxInvocationHandler {

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public AndroidStoreBoxInvocationHandler(StoreEngine engine, Class cls, SaveMode saveMode) {
        super(engine, cls, saveMode);

        prefs = ((SharedPreferencesEngine) engine).getPrefs();
        editor = prefs.edit();
    }

    @Override
    public Object forwardMethod(StoreEngine engine, Method method, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // can we forward the method to the SharedPreferences?
        try {
            final Method prefsMethod = prefs.getClass().getDeclaredMethod(
                    method.getName(),
                    method.getParameterTypes());

            return prefsMethod.invoke(prefs, args);
        } catch (NoSuchMethodException e) {
            // NOP
        }
        // can we forward the method to the SharedPreferences.Editor?
        try {
            final Method editorMethod = editor.getClass().getDeclaredMethod(
                    method.getName(),
                    method.getParameterTypes());

            return editorMethod.invoke(editor, args);
        } catch (NoSuchMethodException e) {
            // NOP
        }
        // Parent forward
        return super.forwardMethod(engine, method, args);
    }

    @Override
    public Object chainingMethod(StoreEngine engine, Method method, Class<?> returnType, Class cls, Object proxy) {
        if (returnType == SharedPreferences.Editor.class) {
            return editor;
        } else {
            // Parent chaining
            return super.chainingMethod(engine, method, returnType, cls, proxy);
        }
    }
}
