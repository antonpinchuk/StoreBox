/*
 * Copyright (c) 2015-2016 Martin Bella
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

package net.orange_box.storebox.harness

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.test.InstrumentationTestCase
import net.orange_box.storebox.StoreBox

fun InstrumentationTestCase.prefs(): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(
            instrumentation.targetContext)
}

inline fun <reified T : Any> InstrumentationTestCase.storeBox(): T {
    return StoreBox.create(instrumentation.targetContext, T::class.java)
}
