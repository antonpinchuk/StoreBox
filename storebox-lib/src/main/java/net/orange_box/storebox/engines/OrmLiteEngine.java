package net.orange_box.storebox.engines;

import net.orange_box.storebox.StoreEngine;
import net.orange_box.storebox.enums.SaveMode;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;

import java.util.Set;

public class OrmLiteEngine implements StoreEngine {

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return null;
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        return null;
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        return null;
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return null;
    }

    @Override
    public String getString(String key, String defaultValue) {
        return null;
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return null;
    }

    @Override
    public void putBoolean(String key, Boolean value, SaveMode mode) {

    }

    @Override
    public void putFloat(String key, Float value, SaveMode mode) {

    }

    @Override
    public void putInt(String key, Integer value, SaveMode mode) {

    }

    @Override
    public void putLong(String key, Long value, SaveMode mode) {

    }

    @Override
    public void putString(String key, String value, SaveMode mode) {

    }

    @Override
    public void putStringSet(String key, Set<String> value, SaveMode mode) {

    }

    @Override
    public void remove(String key, SaveMode mode) {

    }

    @Override
    public void clear(SaveMode mode) {

    }

    @Override
    public Boolean getResourceBoolean(int resourceId) {
        return null;
    }

    @Override
    public Float getResourceFloat(int resourceId) {
        return null;
    }

    @Override
    public Integer getResourceInt(int resourceId) {
        return null;
    }

    @Override
    public Long getResourceLong(int resourceId) {
        return null;
    }

    @Override
    public String getResourceString(int resourceId) {
        return null;
    }

    @Override
    public String getResourceTypeName(int resourceId) {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(ChangeListenerMethodHandler changeHandler) {

    }

}
