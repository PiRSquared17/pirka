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
public class UriUsableValidatorTest extends ValidatorTest {

    @Test
    public void isValid() {
        String msg = "target contains unusable charactor to URI.";
        assertThat(new UriUsableValidator().validate("target", ""), is(nullValue()));
        assertThat(new UriUsableValidator().validate("target", "/index.html"), is(nullValue()));
        assertThat(new UriUsableValidator().validate("target", "aaa/bbb-01"), is(nullValue()));
        assertThat(new UriUsableValidator().validate("target", "?"), is(msg));
        assertThat(new UriUsableValidator().validate("target", "マルチバイト"), is(msg));
        assertThat(new UriUsableValidator().validate("target", "aa.html#tag"), is(msg));
        assertThat(new UriUsableValidator().validate("target", "100%"), is(msg));
        assertThat(new UriUsableValidator().validate("target", "(int)"), is(msg));
    }
}
