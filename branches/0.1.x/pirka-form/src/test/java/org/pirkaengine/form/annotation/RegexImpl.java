package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl extends AbstractValuedAnnotationImpl implements Regex {

    public RegexImpl(String value) {
        this(value, "");
    }

    public RegexImpl(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex.class;
    }

}
