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

package net.orange_box.storebox.harness.types;

import android.net.Uri;
import android.test.suitebuilder.annotation.SmallTest;

import net.orange_box.storebox.harness.base.PreferencesTestCase;
import net.orange_box.storebox.harness.interfaces.types.CustomTypesInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CustomTypesTestCase extends
        PreferencesTestCase<CustomTypesInterface> {

    @Override
    protected Class<CustomTypesInterface> getInterface() {
        return CustomTypesInterface.class;
    }
    
    @SmallTest
    public void testCustomEnumDefaults() {
        assertNull(uut.getCustomEnum());
        assertSame(CustomEnum.ONE, uut.getCustomEnum(CustomEnum.ONE));
    }
    
    @SmallTest
    public void testCustomEnum() {
        uut.setCustomEnum(CustomEnum.TWO);
        assertSame(CustomEnum.TWO, uut.getCustomEnum());
        assertSame(CustomEnum.TWO, uut.getCustomEnum(CustomEnum.ONE));
    }

    @SmallTest
    public void testCustomEnumNull() {
        uut.setCustomEnum(CustomEnum.TWO);
        uut.setCustomEnum(null);
        
        assertNull(uut.getCustomEnum());
    }
    
    @SmallTest
    public void testDateDefaults() {
        assertNull(uut.getDate());
        assertEquals(new Date(0), uut.getDate(new Date(0)));
    }
    
    @SmallTest
    public void testDate() {
        final Date date = new Date();
        
        uut.setDate(date);
        assertEquals(date, uut.getDate());
        assertEquals(date, uut.getDate(new Date(0)));
    }
    
    @SmallTest
    public void testDateNull() {
        uut.setDate(new Date());
        uut.setDate(null);
        
        assertNull(uut.getDate());
    }

    @SmallTest
    public void testDoubleDefaults() {
        assertEquals(0D, uut.getDouble());
        assertEquals(1D, uut.getDouble(1D));
    }

    @SmallTest
    public void testDouble() {
        final double value = 1;

        uut.setDouble(value);
        assertEquals(value, uut.getDouble());
        assertEquals(value, uut.getDouble(0D));
    }

    @SmallTest
    public void testDoubleBoundaries() {
        uut.setDouble(Double.MIN_VALUE);
        assertEquals(Double.MIN_VALUE, uut.getDouble());

        uut.setDouble(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, uut.getDouble());

        uut.setDouble(Double.MIN_NORMAL);
        assertEquals(Double.MIN_NORMAL, uut.getDouble());
    }
    
    @SmallTest
    public void testUriDefaults() {
        assertNull(uut.getUri());
        assertEquals(Uri.EMPTY, uut.getUri(Uri.EMPTY));
    }
    
    @SmallTest
    public void testUri() {
        final Uri value = Uri.parse("http://www.google.com");
        
        uut.setUri(value);
        assertEquals(value, uut.getUri());
        assertEquals(value, uut.getUri(Uri.EMPTY));
    }
    
    @SmallTest
    public void testUriNull() {
        uut.setUri(Uri.parse("http://www.google.com"));
        uut.setUri(null);
        
        assertNull(uut.getUri());
    }

    @SmallTest
    public void testCustomClassDefaults() {
        assertNull(uut.getCustomClass());
        assertEquals(new CustomClass(), uut.getCustomClass(new CustomClass()));
    }

    @SmallTest
    public void testCustomClass() {
        final CustomClass value = new CustomClass("one", "two");

        uut.setCustomClass(value);
        assertEquals(value, uut.getCustomClass());
        assertEquals(value, uut.getCustomClass(new CustomClass()));
    }

    @SmallTest
    public void testCustomClassNull() {
        uut.setCustomClass(new CustomClass("one", "two"));
        uut.setCustomClass(null);

        assertNull(uut.getCustomClass());
    }
    
    @SmallTest
    public void testUriListDefaults() {
        assertNull(uut.getCustomClassList());
        assertEquals(
                new ArrayList<CustomClass>(),
                uut.getCustomClassList(new ArrayList<CustomClass>()));
    }
    
    @SmallTest
    public void testUriList() {
        final List<CustomClass> value = Arrays.asList(
                new CustomClass("a", "b"),
                new CustomClass("b", "c"));
        
        uut.setCustomClassList(value);
        assertEquals(value, uut.getCustomClassList());
    }
    
    @SmallTest
    public void testUriListNull() {
        uut.setCustomClassList(Collections.singletonList(new CustomClass()));
        uut.setCustomClassList(null);
        
        assertNull(uut.getCustomClassList());
    }
}
