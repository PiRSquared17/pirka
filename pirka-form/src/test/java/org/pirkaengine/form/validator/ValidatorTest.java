package org.pirkaengine.form.validator;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.pirkaengine.form.Messages;

public abstract class ValidatorTest {
    @Before
    public void setUp() {
        Messages.setBundle("org.pirkaengine.form.messages", Locale.ENGLISH);
    }

    @After
    public void tearDown() {
        Messages.clearBundle();
    }

}
