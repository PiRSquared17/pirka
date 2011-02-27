package org.pirkaengine.core.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EqualsHelperTest {

    @Before
    public void setup() {
    }

    @Test
    public void strEquals_both_null() {
        assertTrue(EqualsHelper.equals((String) null, (String) null));
    }

    @Test
    public void strEquals_null() {
        assertFalse(EqualsHelper.equals("", (String) null));
    }

    @Test
    public void strEquals_null2() {
        assertFalse(EqualsHelper.equals((String) null, ""));
    }

    @Test
    public void strEquals_true() {
        assertTrue(EqualsHelper.equals("abc", "abc"));
    }

    @Test
    public void strEquals_false() {
        assertFalse(EqualsHelper.equals("abc", "Abc"));
    }
}
