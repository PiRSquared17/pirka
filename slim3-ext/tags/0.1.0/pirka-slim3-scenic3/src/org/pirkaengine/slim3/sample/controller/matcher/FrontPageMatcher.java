package org.pirkaengine.slim3.sample.controller.matcher;

// @javax.annotation.Generated
public class FrontPageMatcher extends scenic3.UrlMatcherImpl {

    private static final FrontPageMatcher INSTANCE = new FrontPageMatcher();
    /**
     * get a instance of this class.
     */
    public static FrontPageMatcher get() {
        return INSTANCE;
    }

    // Constractor.
    private FrontPageMatcher() {
        super("/");
        super.add(new scenic3.UrlPattern("/", "view/{id}"), "org.pirkaengine.slim3.sample.controller._view_id");
        super.add(new scenic3.UrlPattern("/", ""), "org.pirkaengine.slim3.sample.controller.$Index");
    }


}
