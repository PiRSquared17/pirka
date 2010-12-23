package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class StartWithImpl extends AbstractValuedAnnotationImpl implements StartWith {

    public StartWithImpl(String value) {
        this(value, "");
    }

    public StartWithImpl(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return StartWith.class;
    }

}
