package org.pirkaengine.form.validator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexValidator implements Validator<String> {

    private final Pattern pattern;
    
    /**
     * 
     * @param regex
     * @throws PatternSyntaxException
     */
    public RegexValidator(String regex) throws PatternSyntaxException {
        this.pattern = Pattern.compile(regex);
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
