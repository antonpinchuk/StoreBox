package net.orange_box.storebox.engines;

import net.orange_box.storebox.StoreEngine;
import net.orange_box.storebox.enums.SaveMode;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;

import java.util.HashMap;
import java.util.Set;

public class HashMapEngine implements StoreEngine {
    private HashMap<String, Object> map = new HashMap();

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return map.containsKey(key) ? (Boolean) map.get(key) : defaultValue;
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        return map.containsKey(key) ? (Float) map.get(key) : defaultValue;
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        return map.containsKey(key) ? (Integer) map.get(key) : defaultValue;
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return map.containsKey(key) ? (Long) map.get(key) : defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue) {
        return map.containsKey(key) ? (String) map.get(key) : defaultValue;
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return map.containsKey(key) ? (Set<String>) map.get(key) : defaultValue;
    }

    @Override
    public void putBoolean(String key, Boolean value, SaveMode mode) {
        map.put(key, value);
    }

    @Override
    public void putFloat(String key, Float value, SaveMode mode) {
        map.put(key, value);
    }

    @Override
    public void putInt(String key, Integer value, SaveMode mode) {
        map.put(key, value);
    }

    @Override
    public void putLong(String key, Long value, SaveMode mode) {
        map.put(key, value);
    }

    @Override
    public void putString(String key, String value, SaveMode mode) {
        map.put(key, value);
    }

    @Override
    public void putStringSet(String key, Set<String> value, SaveMode mode) {
        map.put(key, value);
    }

    @Override
    public void remove(String key, SaveMode mode) {
        map.remove(key);
    }

    @Override
    public void clear(SaveMode mode) {
        map.clear();
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

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

}
