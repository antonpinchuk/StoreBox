package net.orange_box.storebox.example.ormlite;

import net.orange_box.storebox.StoreEngine;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;

import java.util.HashMap;
import java.util.Set;

public class OrmLiteEngine implements StoreEngine {
    HashMap<String, Object> storageStub = new HashMap();

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return storageStub.containsKey(key) ? (Boolean) storageStub.get(key) : defaultValue;
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        return storageStub.containsKey(key) ? (Float) storageStub.get(key) : defaultValue;
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        return storageStub.containsKey(key) ? (Integer) storageStub.get(key) : defaultValue;
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return storageStub.containsKey(key) ? (Long) storageStub.get(key) : defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue) {
        return storageStub.containsKey(key) ? (String) storageStub.get(key) : defaultValue;
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return storageStub.containsKey(key) ? (Set<String>) storageStub.get(key) : defaultValue;
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        storageStub.put(key, value);
    }

    @Override
    public void putFloat(String key, Float value) {
        storageStub.put(key, value);
    }

    @Override
    public void putInt(String key, Integer value) {
        storageStub.put(key, value);
    }

    @Override
    public void putLong(String key, Long value) {
        storageStub.put(key, value);
    }

    @Override
    public void putString(String key, String value) {
        storageStub.put(key, value);
    }

    @Override
    public void putStringSet(String key, Set<String> value) {
        storageStub.put(key, value);
    }

    @Override
    public void remove(String key) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void apply() {

    }

    @Override
    public void commit() {

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
