package org.pirkaengine.core;


import org.junit.Before;
import org.junit.Test;
import org.pirkaengine.core.PirkaLoadException;
import org.pirkaengine.core.PirkaLoader;
import org.pirkaengine.core.TemplateNotFoundException;
import org.pirkaengine.core.parser.ParseException;

public class PirkaLoaderTest {

    private PirkaLoader target;

    @Before
    public void setup() {
        target = new PirkaLoader();
    }

    @Test(expected = IllegalArgumentException.class)
    public void load_null() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        target.load(null);
    }

    @Test(expected = TemplateNotFoundException.class)
    public void load_no_exist_file() throws ParseException, PirkaLoadException, TemplateNotFoundException {
        target.load("dummy.html");
    }

}
