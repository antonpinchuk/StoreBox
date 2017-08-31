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

package net.orange_box.storebox.annotations.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which should be used on set/get methods to declare the key formated by String.format()
 * Format args must be specified prior to get's default value or set's key
 * <pre>
 * Examples usage {@code
 *
 *     \u0040KeyByFormattedString("%s_string_key_%d")
 *     String getString(String prefix, int index, String defaultValue);
 *
 *     \u0040KeyByFormattedString("%s_string_key_%d")
 *     void setString(String prefix, int index, String value);
 * }
 * @see KeyByString
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KeyByFormattedString {
    
    String value();
}
