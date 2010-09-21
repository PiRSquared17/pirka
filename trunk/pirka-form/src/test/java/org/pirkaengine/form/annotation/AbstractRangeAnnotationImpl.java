package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

public abstract class AbstractRangeAnnotationImpl<T> {

    final T min;
    final T max;
    private final String messageKey;

    public AbstractRangeAnnotationImpl(T min, T max, String messageKey) {
        this.min = min;
        this.max = max;
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
        return (127 * "messageKey".hashCode() ^ messageKey.hashCode())
                + (127 * "min".hashCode() ^ (min != null ? min.hashCode() : 0))
                + (127 * "max".hashCode() ^ (max != null ? max.hashCode() : 0));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractRangeAnnotationImpl<?> other = (AbstractRangeAnnotationImpl<?>) obj;
        if (min == null) {
            if (other.min != null) return false;
        } else if (!min.equals(other.min)) return false;
        if (max == null) {
            if (other.max != null) return false;
        } else if (!max.equals(other.max)) return false;
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
        return "@" + annotationType().getName() + "(min=" + min + ", max=" + max + ", messageKey=" + messageKey + ")";
    }
}
