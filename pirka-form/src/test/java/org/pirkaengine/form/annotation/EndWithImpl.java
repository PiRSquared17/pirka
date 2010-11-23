package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class EndWithImpl extends AbstractValuedAnnotationImpl implements EndWith {

    public EndWithImpl(String value) {
        this(value, "");
    }

    public EndWithImpl(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return EndWith.class;
    }

}
