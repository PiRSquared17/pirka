package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

public abstract class AbstractAnnotationImpl {

    private final String messageKey;

    public AbstractAnnotationImpl(String messageKey) {
        this.messageKey = messageKey;
    }

    public final String messageKey() {
        return messageKey;
    }
    
    public abstract Class<? extends Annotation> annotationType();
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return (127 * "messageKey".hashCode() ^ (messageKey != null ? messageKey.hashCode() : 0));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractAnnotationImpl other = (AbstractAnnotationImpl) obj;
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
        return "@" + annotationType().getName() + "(messageKey=" + messageKey + ")";
    }
}
