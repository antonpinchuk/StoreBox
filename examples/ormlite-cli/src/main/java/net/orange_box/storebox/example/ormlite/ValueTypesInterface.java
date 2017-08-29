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

package net.orange_box.storebox.example.ormlite;

import net.orange_box.storebox.annotations.method.KeyByString;

import java.util.Set;

public interface ValueTypesInterface {
    
    @KeyByString("boolean")
    Boolean getBoolean();
    
    @KeyByString("boolean")
    void setBoolean(Boolean value);


    @KeyByString("float")
    float getFloat();

    @KeyByString("float")
    void setFloat(float value);


    @KeyByString("int")
    int getInt();

    @KeyByString("int")
    void setInt(int value);


    @KeyByString("long")
    long getLong();

    @KeyByString("long")
    void setLong(long value);


    @KeyByString("string")
    String getString();

    @KeyByString("string")
    void setString(String value);


    @KeyByString("string_set")
    Set<String> getStringSet();

    @KeyByString("string_set")
    void setStringSet(Set<String> value);

}
