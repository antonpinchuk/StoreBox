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

package net.orange_box.storebox.utils;

import net.orange_box.storebox.StoreEngine;
import net.orange_box.storebox.adapters.StoreType;
import net.orange_box.storebox.enums.SaveMode;

import java.util.Locale;
import java.util.Set;

public final class PreferenceUtils {
    
    public static Object getValue(
            StoreEngine engine,
            String key,
            StoreType type,
            Object defValue) {
        
        switch (type) {
            case BOOLEAN:
                return engine.getBoolean(key, (Boolean) defValue);
            
            case FLOAT:
                return engine.getFloat(key, (Float) defValue);
            
            case INTEGER:
                return engine.getInt(key, (Integer) defValue);
            
            case LONG:
                return engine.getLong(key, (Long) defValue);
            
            case STRING:
                return engine.getString(key, (String) defValue);

            case STRING_SET:
                return engine.getStringSet(key, (Set<String>) defValue);

            default:
                throw new UnsupportedOperationException(String.format(
                        Locale.ENGLISH,
                        "Retrieving type %1$s from the preferences is not supported",
                        type.name()));
        }
    }
    
    public static void putValue(
            StoreEngine engine,
            String key,
            StoreType type,
            Object value,
            SaveMode mode) {
        
        switch (type) {
            case BOOLEAN:
                engine.putBoolean(key, (Boolean) value, mode);
                break;
            
            case FLOAT:
                engine.putFloat(key, (Float) value, mode);
                break;
            
            case INTEGER:
                engine.putInt(key, (Integer) value, mode);
                break;
            
            case LONG:
                engine.putLong(key, (Long) value, mode);
                break;
            
            case STRING:
                engine.putString(key, (String) value, mode);
                break;

            case STRING_SET:
                engine.putStringSet(key, (Set<String>) value, mode);
                break;

            default:
                throw new UnsupportedOperationException(String.format(
                        Locale.ENGLISH,
                        "Saving type %1$s into the preferences is not supported",
                        type.name()));
        }
    }
    
    //public static void saveChanges(
    //        StoreEngine engine,
    //        SaveMode mode) {
    //
    //    switch (mode) {
    //        case APPLY:
    //            engine.apply();
    //            break;
    //
    //        case COMMIT:
    //            engine.commit();
    //            break;
    //
    //        case NOME:
    //        default:
    //            // NOP
    //    }
    //}
    
    private PreferenceUtils() {}
}
