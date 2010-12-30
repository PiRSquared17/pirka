package org.pirkaengine.slim3.sample.controller;

// Controller for org.pirkaengine.slim3.sample.FrontPage#index
// @javax.annotation.Generated
public final class $Index extends scenic3.ScenicController {

    private final org.pirkaengine.slim3.sample.FrontPage page;

    public $Index() {
        this.page = new org.pirkaengine.slim3.sample.FrontPage();
    }

    @Override
    public final org.slim3.controller.Navigation run() throws Exception {
        setupPage(page);
        return page.index();
    }

    @Override
    public final org.pirkaengine.slim3.sample.FrontPage getPage() {
        return this.page;
    }

    @Override
    protected final org.slim3.controller.Navigation handleError(Throwable error) throws Throwable {
        return page.handleError(error);
    }

    @Override
    public final String getActionMethodName() {
        return "index";
    }

}
