package org.pirkaengine.mobile.tester;

import org.junit.Before;

public class MobileTestCase {
    protected MobileTester tester;

    @Before
    public void setUp() {
        tester = new MobileTester();
    }

}
