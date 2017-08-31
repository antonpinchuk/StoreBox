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

package net.orange_box.storebox.harness.advanced;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import net.orange_box.storebox.StoreBox;
import net.orange_box.storebox.harness.interfaces.advanced.FormattedKeysInterface;

public class FormattedKeysTestCase extends InstrumentationTestCase {
    
    private FormattedKeysInterface uut;
    private SharedPreferences prefs;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        uut = StoreBox.create(
                getInstrumentation().getTargetContext(), FormattedKeysInterface.class);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(
                getInstrumentation().getTargetContext());
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void tearDown() throws Exception {
        uut = null;
        
        // we are saving to the actual preferences so let's clear them
        prefs.edit().clear().commit();
        prefs = null;
        
        super.tearDown();
    }
    
    @SmallTest
    public void testInt() {
        assertEquals(0, uut.getInt(0));
        assertEquals(0, uut.getInt(1));

        uut.setInt(0, 1);
        uut.setInt(1, 2);

        assertEquals(1, uut.getInt(0));
        assertEquals(2, uut.getInt(1));
    }

    @SmallTest
    public void testString() {
        assertNull(uut.getString("index_0"));
        assertNull(uut.getString("index_1"));

        uut.setString("index_0", "value_1");
        uut.setString("index_1", "value_2");

        assertEquals("value_1", uut.getString("index_0"));
        assertEquals("value_2", uut.getString("index_1"));
    }

    @SmallTest
    public void testStringByComplexKey() {
        assertNull(uut.getStringByComplexKey("prefix_0", 0));
        assertNull(uut.getStringByComplexKey("prefix_0", 1));
        assertNull(uut.getStringByComplexKey("prefix_1", 0));
        assertNull(uut.getStringByComplexKey("prefix_1", 1));

        assertEquals("string", uut.getStringByComplexKey("prefix_0", 0, "string"));
        assertEquals("string", uut.getStringByComplexKey("prefix_0", 1, "string"));
        assertEquals("string", uut.getStringByComplexKey("prefix_1", 0, "string"));
        assertEquals("string", uut.getStringByComplexKey("prefix_1", 1, "string"));

        uut.setStringByComplexKey("prefix_0", 0, "value_1");
        uut.setStringByComplexKey("prefix_0", 1, "value_2");
        uut.setStringByComplexKey("prefix_1", 0, "value_3");
        uut.setStringByComplexKey("prefix_1", 1, "value_4");

        assertEquals("value_1", uut.getStringByComplexKey("prefix_0", 0));
        assertEquals("value_2", uut.getStringByComplexKey("prefix_0", 1));
        assertEquals("value_3", uut.getStringByComplexKey("prefix_1", 0));
        assertEquals("value_4", uut.getStringByComplexKey("prefix_1", 1));
    }

}
