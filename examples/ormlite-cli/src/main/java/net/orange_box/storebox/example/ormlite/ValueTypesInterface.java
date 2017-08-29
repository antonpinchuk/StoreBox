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
    boolean getBoolean();

    @KeyByString("boolean")
    boolean getBoolean(boolean defValue);

    @KeyByString("boolean")
    Boolean getBooleanObj();

    @KeyByString("boolean")
    Boolean getBooleanObj(Boolean defValue);

    @KeyByString("boolean")
    void setBoolean(boolean value);


    @KeyByString("float")
    float getFloat();

    @KeyByString("float")
    float getFloat(float defValue);

    @KeyByString("float")
    Float getFloatObj();

    @KeyByString("float")
    Float getFloatObj(Float defValue);

    @KeyByString("float")
    void setFloat(float value);


    @KeyByString("int")
    int getInt();

    @KeyByString("int")
    int getInt(int defValue);

    @KeyByString("int")
    Integer getIntObj();

    @KeyByString("int")
    Integer getIntObj(Integer defValue);

    @KeyByString("int")
    void setInt(int value);


    @KeyByString("long")
    long getLong();

    @KeyByString("long")
    void setLong(long value);


    @KeyByString("string")
    String getString();

    @KeyByString("string")
    String getString(String defValue);

    @KeyByString("string")
    void setString(String value);


    @KeyByString("string_set")
    Set<String> getStringSet();

    @KeyByString("string_set")
    void setStringSet(Set<String> value);

}
