package org.pirkaengine.form.validator;

import java.util.Locale;

import org.junit.Before;
import org.pirkaengine.form.FormMessage;
import org.pirkaengine.form.ResourceBundleFormMessage;

public abstract class ValidatorTest {
    protected FormMessage formMessage;

    @Before
    public void setUp() {
        formMessage = new ResourceBundleFormMessage("org.pirkaengine.form.messages", Locale.ENGLISH);
    }

}
