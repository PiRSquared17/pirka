package org.pirkaengine.slim3.sample;

import java.util.ArrayList;
import java.util.List;

import org.pirkaengine.slim3.PirkaPage;
import org.slim3.controller.Navigation;

import scenic3.annotation.ActionPath;
import scenic3.annotation.Default;
import scenic3.annotation.Page;
import scenic3.annotation.Var;

@Page("/")
public class FrontPage extends PirkaPage {
    // /view/100  /view/200
    @ActionPath("view/{id}")
    public Navigation view(@Var("id") String id) throws Exception {
        viewModel("id", id);
        return render("view.html");
    }

    // /
    @Default
    public Navigation index() throws Exception {
        List<Item> list = new ArrayList<Item>();
        list.add(new Item(1, "Book", 3200));
        list.add(new Item(2, "Game", 7800));
        list.add(new Item(3, "Music Video", 2800));
        viewModel("list", list);
        viewModel("title", "Wish List");
        return render("index.html");
    }
}