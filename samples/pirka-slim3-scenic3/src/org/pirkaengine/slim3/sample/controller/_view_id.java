package org.pirkaengine.slim3.sample.controller;

// Controller for org.pirkaengine.slim3.sample.FrontPage#view
// @javax.annotation.Generated
public final class _view_id extends scenic3.ScenicController {

    private final org.pirkaengine.slim3.sample.FrontPage page;

    public _view_id() {
        this.page = new org.pirkaengine.slim3.sample.FrontPage();
    }

    @Override
    public final org.slim3.controller.Navigation run() throws Exception {
        setupPage(page);
        return page.view(super.var("id"));
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
        return "view";
    }

}
