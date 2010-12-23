package org.pirkaengine.form.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * Test for {@link EndWithValidator}
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class EndWithValidatorTest extends ValidatorTest {

    @Test
    public void isValid() throws Exception {
        EndWithValidator target = new EndWithValidator("/");
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", "/"), is(nullValue()));
        assertThat(target.validate("target", "aaa/"), is(nullValue()));
        assertThat(target.validate("target", "aaa"), is("target is not end with '/'."));
    }

    @Test
    public void isValid_html() throws Exception {
        EndWithValidator target = new EndWithValidator(".html");
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", ".html"), is(nullValue()));
        assertThat(target.validate("target", "xy.html"), is(nullValue()));
        assertThat(target.validate("target", "aaa.htm"), is("target is not end with '.html'."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValid_null() throws Exception {
        EndWithValidator target = new EndWithValidator("xy");
        target.setFormMessage(formMessage);
        target.validate("target", null);
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_null() throws Exception {
        new EndWithValidator(null);
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_empty_string() throws Exception {
        new EndWithValidator("");
    }
}
