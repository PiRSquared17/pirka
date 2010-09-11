package org.pirkaengine.form.validator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for {@link UriUsableValidator}.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class UriUsableValidatorTest {

    @Test
    public void isValid() {
        assertThat(new UriUsableValidator().isValid(""), is(true));
        assertThat(new UriUsableValidator().isValid("/index.html"), is(true));
        assertThat(new UriUsableValidator().isValid("aaa/bbb-01"), is(true));
        assertThat(new UriUsableValidator().isValid("?"), is(false));
        assertThat(new UriUsableValidator().isValid("マルチバイト"), is(false));
        assertThat(new UriUsableValidator().isValid("aa.html#tag"), is(false));
        assertThat(new UriUsableValidator().isValid("100%"), is(false));
        assertThat(new UriUsableValidator().isValid("(int)"), is(false));
    }
}
