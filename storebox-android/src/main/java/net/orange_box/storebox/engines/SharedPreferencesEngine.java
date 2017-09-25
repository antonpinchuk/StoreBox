package net.orange_box.storebox.engines;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import net.orange_box.storebox.StoreEngine;
import net.orange_box.storebox.enums.SaveMode;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;

import java.util.Set;

/**
 * Android SharedPreferences as storage engine
 */
public class SharedPreferencesEngine implements StoreEngine {

    public SharedPreferences getPrefs() {
        return prefs;
    }

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    Context context;

    public SharedPreferencesEngine(Context context) {
        this.context = context;
    }

    public void setSharedPreferences(SharedPreferences preferences) {
        this.prefs = preferences;
        editor = prefs.edit();
    }


    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    @Override
    public java.lang.Float getFloat(String key, java.lang.Float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return prefs.getStringSet(key, defaultValue);
        } else {
            return null;
        }
    }

    @Override
    public void putBoolean(String key, Boolean value, SaveMode mode) {
        editor.putBoolean(key, value);
        commitOrApply(mode);
    }

    @Override
    public void putFloat(String key, Float value, SaveMode mode) {
        editor.putFloat(key, value);
        commitOrApply(mode);
    }

    @Override
    public void putInt(String key, Integer value, SaveMode mode) {
        editor.putInt(key, value);
        commitOrApply(mode);
    }

    @Override
    public void putLong(String key, Long value, SaveMode mode) {
        editor.putLong(key, value);
        commitOrApply(mode);
    }

    @Override
    public void putString(String key, String value, SaveMode mode) {
        editor.putString(key, value);
        commitOrApply(mode);
   }

    @Override
    public void putStringSet(String key, Set<String> value, SaveMode mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            editor.putStringSet(key, value);
            commitOrApply(mode);
        }
    }

    @Override
    public void remove(String key, SaveMode mode) {
        editor.remove(key);
        commitOrApply(mode);
    }

    @Override
    public void clear(SaveMode mode) {
        editor.clear();
        commitOrApply(mode);
    }

    @Override
    public Boolean getResourceBoolean(int resourceId) {
        return context.getResources().getBoolean(resourceId);
    }

    @Override
    public java.lang.Float getResourceFloat(int resourceId) {
        final TypedValue value = new TypedValue();
        context.getResources().getValue(resourceId, value, true);
        return value.getFloat();
    }

    @Override
    public Integer getResourceInt(int resourceId) {
        return context.getResources().getInteger(resourceId);
    }

    @Override
    public Long getResourceLong(int resourceId) {
        final TypedValue value = new TypedValue();
        context.getResources().getValue(resourceId, value, true);
        return Long.valueOf(value.data);
    }

    @Override
    public String getResourceString(int resourceId) {
        return context.getResources().getString(resourceId);
    }

    @Override
    public String getResourceTypeName(int resourceId) {
        return context.getResources().getResourceTypeName(resourceId);
    }


    @Override
    public void registerOnSharedPreferenceChangeListener(final ChangeListenerMethodHandler changeHandler) {
        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                changeHandler.onSharedPreferenceChanged(SharedPreferencesEngine.this, s);
            }
        });
    }

    private void commitOrApply(SaveMode mode) {
        if (mode == SaveMode.APPLY) {
            editor.apply();
        } else if (mode == SaveMode.COMMIT) {
            editor.commit();
        }
    }

}
