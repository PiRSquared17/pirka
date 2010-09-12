package org.pirkaengine.form.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * Test for {@link StartWithValidator}
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class StartWithValidatorTest extends ValidatorTest {

    @Test
    public void isValid() throws Exception {
        StartWithValidator target = new StartWithValidator("/");
        assertThat(target.validate("target", "/"), is(nullValue()));
        assertThat(target.validate("target", "/aaa"), is(nullValue()));
        assertThat(target.validate("target", "aaa"), is("target is not start with '/'."));
    }

    @Test
    public void isValid_xy() throws Exception {
        StartWithValidator target = new StartWithValidator("xy");
        assertThat(target.validate("target", "x"), is("target is not start with 'xy'."));
        assertThat(target.validate("target", "xy"), is(nullValue()));
        assertThat(target.validate("target", "xyz"), is(nullValue()));
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_null() throws Exception {
        new StartWithValidator(null);
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_empty_string() throws Exception {
        new StartWithValidator("");
    }
}
