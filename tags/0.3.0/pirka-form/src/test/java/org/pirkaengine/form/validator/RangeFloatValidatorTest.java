package org.pirkaengine.form.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.exception.ValidatorFormatException;

public class RangeFloatValidatorTest extends ValidatorTest {

    @Test
    public void validate_1_10() throws Exception {
        RangeFloatValidator target = new RangeFloatValidator(0f, 1f);
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", -0.01f), is("target is not in the 0 to 1 range."));
        assertThat(target.validate("target", 0f), is(nullValue()));
        assertThat(target.validate("target", 1f), is(nullValue()));
        assertThat(target.validate("target", 1.1f), is("target is not in the 0 to 1 range."));
    }

    @Test
    public void validate_0_1() throws Exception {
        RangeFloatValidator target = new RangeFloatValidator(1f, 10f);
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", 0.99f), is("target is not in the 1 to 10 range."));
        assertThat(target.validate("target", 1f), is(nullValue()));
        assertThat(target.validate("target", 10f), is(nullValue()));
        assertThat(target.validate("target", 10.1f), is("target is not in the 1 to 10 range."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValid_null() throws Exception {
        RangeFloatValidator target = new RangeFloatValidator(0, 1);
        target.setFormMessage(formMessage);
        target.validate("target", null);
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_wrong() throws Exception {
        new RangeFloatValidator(0, 0);
    }

}
