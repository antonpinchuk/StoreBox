package net.orange_box.storebox;


import net.orange_box.storebox.adapters.standard.Float;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;

import java.util.Set;

public interface StoreEngine {

    Boolean getBoolean(String key, Boolean defaultValue);
    java.lang.Float getFloat(String key, java.lang.Float defaultValue);
    Integer getInt(String key, Integer defaultValue);
    Long getLong(String key, Long defaultValue);
    String getString(String key, String defaultValue);
    Set<String> getStringSet(String key, Set<String> defaultValue);

    void putBoolean(String key, Boolean value);
    void putFloat(String key, java.lang.Float value);
    void putInt(String key, Integer value);
    void putLong(String key, Long value);
    void putString(String key, String value);
    void putStringSet(String key, Set<String> value);

    void remove(String key);
    void clear();

    void apply();
    void commit();

    Boolean getResourceBoolean(int resourceId);
    java.lang.Float getResourceFloat(int resourceId);
    Integer getResourceInt(int resourceId);
    Long getResourceLong(int resourceId);
    String getResourceString(int resourceId);
    String getResourceTypeName(int resourceId);

    void registerOnSharedPreferenceChangeListener(ChangeListenerMethodHandler changeHandler);

}
