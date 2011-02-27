package org.pirkaengine.slim3.sample.controller;

import org.pirkaengine.slim3.PirkaController;
import org.slim3.controller.Navigation;

public class IndexController extends PirkaController {

    @Override
    public Navigation run() throws Exception {
        viewModel("title", "Hello GAE.");
        viewModel("list", new String[] {"Python", "Java"});
        return render("index.html");
    }
}
