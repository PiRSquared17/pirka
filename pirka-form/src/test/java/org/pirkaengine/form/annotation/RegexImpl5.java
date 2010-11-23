package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl5 extends AbstractValuedAnnotationImpl implements Regex5 {

    public RegexImpl5(String value) {
        this(value, "");
    }

    public RegexImpl5(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex5.class;
    }

}
