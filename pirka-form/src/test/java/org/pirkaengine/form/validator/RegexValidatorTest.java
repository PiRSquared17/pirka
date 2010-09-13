package org.pirkaengine.form.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * Test for {@link RegexValidator}
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RegexValidatorTest extends ValidatorTest {

    @Test
    public void isValid() throws Exception {
        RegexValidator target = new RegexValidator("/.*");
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", "/"), is(nullValue()));
        assertThat(target.validate("target", "/aaa"), is(nullValue()));
        assertThat(target.validate("target", "aaa"), is("target is invalid pattern."));
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_illegal_pattern() throws Exception {
        new RegexValidator("\\");
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_null() throws Exception {
        new RegexValidator(null);
    }
}
