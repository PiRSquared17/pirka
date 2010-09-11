package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl  implements Regex {

    private final String value;

    public RegexImpl(String value) {
        this.value = value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex.class;
    }

    @Override
    public String value() {
        return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (127 * "value".hashCode() ^ (value != null ? value.hashCode() : 0));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RegexImpl other = (RegexImpl) obj;
        if (value == null) {
            if (other.value != null) return false;
        } else if (!value.equals(other.value)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "@" + Regex.class.getName() + "(value=" + value + ")";
    }

}

