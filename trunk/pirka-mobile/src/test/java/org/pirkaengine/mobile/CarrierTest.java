package org.pirkaengine.mobile;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.mobile.tester.MobileTestCase;

public class CarrierTest extends MobileTestCase {

    @Test
    public void detect_Docomo() throws Exception {
        tester.setUserAgent("DoCoMo/1.0/XXX/YYY/");
        assertThat(Carrier.detect(tester.request), is(sameInstance(Carrier.DOCOMO)));
    }

    @Test
    public void detect_AU() throws Exception {
        tester.setUserAgent("KDDI/1.0/XXX/YYY/");
        assertThat(Carrier.detect(tester.request), is(sameInstance(Carrier.KDDI)));
    }

    @Test
    public void detect_SoftBank() throws Exception {
        tester.setUserAgent("SoftBank/1.0/XXX/YYY/");
        assertThat(Carrier.detect(tester.request), is(sameInstance(Carrier.SOFTBANK)));
    }

    @Test
    public void detect_Vodafone() throws Exception {
        tester.setUserAgent("Vodafone/1.0/XXX/YYY/");
        assertThat(Carrier.detect(tester.request), is(sameInstance(Carrier.SOFTBANK)));
    }

    @Test
    public void detect_MOT() throws Exception {
        tester.setUserAgent("MOT/1.0/XXX/YYY/");
        assertThat(Carrier.detect(tester.request), is(sameInstance(Carrier.SOFTBANK)));
    }

    @Test
    public void detect_nullAgent() throws Exception {
        tester.setUserAgent(null);
        assertThat(Carrier.detect(tester.request), is(sameInstance(Carrier.UNKOWN)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void detect_null() throws Exception {
        Carrier.detect(null);
    }
}
