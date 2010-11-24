package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

public abstract class AbstractValuedAnnotationImpl {

    private final String value;
    private final String messageKey;

    public AbstractValuedAnnotationImpl(String value, String messageKey) {
        this.value = value;
        this.messageKey = messageKey;
    }

    public final String messageKey() {
        return messageKey;
    }

    public final String value() {
        return value;
    }

    public abstract Class<? extends Annotation> annotationType();

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return (127 * "messageKey".hashCode() ^ messageKey.hashCode())
                + (127 * "value".hashCode() ^ (value != null ? value.hashCode() : 0));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractValuedAnnotationImpl other = (AbstractValuedAnnotationImpl) obj;
        if (value == null) {
            if (other.value != null) return false;
        } else if (!value.equals(other.value)) return false;
        if (messageKey == null) {
            if (other.messageKey != null) return false;
        } else if (!messageKey.equals(other.messageKey)) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return "@" + annotationType().getName() + "(value=" + value + ", messageKey=" + messageKey + ")";
    }
}
