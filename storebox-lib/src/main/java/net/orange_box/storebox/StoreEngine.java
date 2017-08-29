package net.orange_box.storebox;

import net.orange_box.storebox.enums.SaveMode;
import net.orange_box.storebox.handlers.ChangeListenerMethodHandler;

import java.util.Set;

public interface StoreEngine {

    Boolean getBoolean(String key, Boolean defaultValue);
    Float getFloat(String key, Float defaultValue);
    Integer getInt(String key, Integer defaultValue);
    Long getLong(String key, Long defaultValue);
    String getString(String key, String defaultValue);
    Set<String> getStringSet(String key, Set<String> defaultValue);

    void putBoolean(String key, Boolean value, SaveMode mode);
    void putFloat(String key, Float value, SaveMode mode);
    void putInt(String key, Integer value, SaveMode mode);
    void putLong(String key, Long value, SaveMode mode);
    void putString(String key, String value, SaveMode mode);
    void putStringSet(String key, Set<String> value, SaveMode mode);

    void remove(String key, SaveMode mode);
    void clear(SaveMode mode);

    // Android Resource callbacks for backward compatibility
    Boolean getResourceBoolean(int resourceId);
    Float getResourceFloat(int resourceId);
    Integer getResourceInt(int resourceId);
    Long getResourceLong(int resourceId);
    String getResourceString(int resourceId);
    String getResourceTypeName(int resourceId);

    void registerOnSharedPreferenceChangeListener(ChangeListenerMethodHandler changeHandler);

}
