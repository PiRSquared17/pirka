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
public class StartWithValidatorTest {

    @Test
    public void isValid() throws Exception {
        StartWithValidator target = new StartWithValidator("/");
        assertThat(target.isValid("/"), is(true));
        assertThat(target.isValid("/aaa"), is(true));
        assertThat(target.isValid("aaa"), is(false));
    }

    @Test
    public void isValid_xy() throws Exception {
        StartWithValidator target = new StartWithValidator("xy");
        assertThat(target.isValid("x"), is(false));
        assertThat(target.isValid("xy"), is(true));
        assertThat(target.isValid("xyz"), is(true));
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