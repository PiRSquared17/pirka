package org.pirkaengine.form.validator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.pirkaengine.form.exception.ValidatorFormatException;

/**
 * 正規表現Validatorクラス.
 * <p></p>
 * @author shuji.w6e
 * @since 0.1.0
 */
public class RegexValidator implements Validator<String> {

    private final Pattern pattern;

    /**
     * 正規表現パターンを指定してインスタンスを生成する.
     * @param regex 正規表現パターン
     * @throws ValidatorFormatException 正規表現パターンが不正な場合
     */
    public RegexValidator(String regex) throws ValidatorFormatException {
        try {
            this.pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            throw new ValidatorFormatException("RegexValidator: " + regex, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.pirkaengine.form.validator.Validator#isValid(java.lang.Object)
     */
    @Override
    public boolean isValid(String value) {
        return pattern.matcher(value).matches();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RegexValidator other = (RegexValidator) obj;
        if (!pattern.pattern().equals(other.pattern.pattern())) return false;
        return true;
    }

}
