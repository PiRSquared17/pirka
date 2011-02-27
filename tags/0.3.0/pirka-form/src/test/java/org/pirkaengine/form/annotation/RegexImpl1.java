package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl1 extends AbstractValuedAnnotationImpl implements Regex1 {

    public RegexImpl1(String value) {
        this(value, "");
    }

    public RegexImpl1(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex1.class;
    }

}
