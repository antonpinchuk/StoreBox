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

package net.orange_box.storebox.harness.interfaces.advanced;

import net.orange_box.storebox.annotations.method.KeyByFormattedString;

public interface FormattedKeysInterface {

    @KeyByFormattedString("int_%d_key")
    int getInt(int index);

    @KeyByFormattedString("int_%d_key")
    void setInt(int index, int value);

    @KeyByFormattedString("string_%s_key")
    String getString(String index);

    @KeyByFormattedString("string_%s_key")
    void setString(String index, String value);

    @KeyByFormattedString("%s_string_%d")
    String getStringByComplexKey(String prefix, int index);

    @KeyByFormattedString("%s_string_%d")
    String getStringByComplexKey(String prefix, int index, String defaultValue);

    @KeyByFormattedString("%s_string_%d")
    void setStringByComplexKey(String prefix, int index, String value);

}
