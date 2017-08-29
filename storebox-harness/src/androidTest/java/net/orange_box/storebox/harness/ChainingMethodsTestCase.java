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

package net.orange_box.storebox.harness;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import net.orange_box.storebox.StoreBox;
import net.orange_box.storebox.harness.interfaces.ChainingMethodsInterface;

public class ChainingMethodsTestCase extends InstrumentationTestCase {

    private ChainingMethodsInterface uut;
    private SharedPreferences prefs;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        uut = StoreBox.create(
                getInstrumentation().getTargetContext(),
                ChainingMethodsInterface.class);

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
    public void testChainingSetMethods() {
        // SharedPreferences are now out of StoreBox, so we can't chain
        //assertTrue(uut.setFirstValue("one") instanceof SharedPreferences.Editor);
        assertSame(uut, uut.setSecondValue("two"));
    }

    @SmallTest
    public void testChainingRemoveMethods() {
        // SharedPreferences are now out of StoreBox, so we can't chain
        //assertTrue(uut.removeFirstValue() instanceof SharedPreferences.Editor);
        assertSame(uut, uut.removeSecondValue());
    }
}
