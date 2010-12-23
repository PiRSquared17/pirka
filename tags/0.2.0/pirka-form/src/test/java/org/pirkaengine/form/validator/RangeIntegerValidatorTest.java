package org.pirkaengine.form.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pirkaengine.form.exception.ValidatorFormatException;

public class RangeIntegerValidatorTest extends ValidatorTest {

    @Test
    public void validate_1_10() throws Exception {
        RangeIntegerValidator target = new RangeIntegerValidator(1, 10);
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", 0), is("target is not in the 1 to 10 range."));
        assertThat(target.validate("target", 1), is(nullValue()));
        assertThat(target.validate("target", 10), is(nullValue()));
        assertThat(target.validate("target", 11), is("target is not in the 1 to 10 range."));
    }

    @Test
    public void validate_0_1() throws Exception {
        RangeIntegerValidator target = new RangeIntegerValidator(0, 1);
        target.setFormMessage(formMessage);
        assertThat(target.validate("target", -1), is("target is not in the 0 to 1 range."));
        assertThat(target.validate("target", 0), is(nullValue()));
        assertThat(target.validate("target", 1), is(nullValue()));
        assertThat(target.validate("target", 2), is("target is not in the 0 to 1 range."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValid_null() throws Exception {
        RangeIntegerValidator target = new RangeIntegerValidator(0, 1);
        target.setFormMessage(formMessage);
        target.validate("target", null);
    }

    @Test(expected = ValidatorFormatException.class)
    public void const_wrong() throws Exception {
        new RangeIntegerValidator(0, 0);
    }

}
