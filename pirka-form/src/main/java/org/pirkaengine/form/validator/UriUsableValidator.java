package org.pirkaengine.form.validator;

import java.util.regex.Pattern;

/**
 * 特定の文字列で開始されているかを判定するValidator.
 * @author shuji.w6e
 * @since 0.1.0
 */
public class UriUsableValidator implements Validator<String> {

    private static final Pattern PATTERN = Pattern.compile("[\\w-\\./~,$!\\*';:@=&+]*");

    /**
     * Constructor.
     */
    public UriUsableValidator() {
    }

    /* (non-Javadoc)
     * @see org.pirkaengine.form.validator.Validator#isValid(java.lang.String)
     */
    @Override
    public boolean isValid(String value) {
        return PATTERN.matcher(value).matches();
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        return obj.getClass() == this.getClass();
    }
}
