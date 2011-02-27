package org.pirkaengine.mobile.tester;

public class MobileTester {

    public final MockServletContext ctx = new MockServletContext();
    public final MockHttpServletRequest request = new MockHttpServletRequest(ctx);

    public void setUserAgent(String userAgent) {
        request.setHeader("User-Agent", userAgent);
    }
}
