package org.pirkaengine.slim3.sample.controller;

import scenic3.UrlsImpl;
import org.pirkaengine.slim3.sample.controller.matcher.FrontPageMatcher;

public class AppUrls extends UrlsImpl {

    public AppUrls() {
        excludes("/css/*");
        add(FrontPageMatcher.get());
        // TODO Add your own new PageMatcher

    }
}