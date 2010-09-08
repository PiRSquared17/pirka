package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RequiredImpl implements Required {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Required.class;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 1;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "@" + Required.class.getName() + "()";
    }

}
