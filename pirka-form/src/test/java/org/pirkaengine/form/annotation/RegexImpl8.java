package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl8 extends AbstractValuedAnnotationImpl implements Regex8 {

    public RegexImpl8(String value) {
        this(value, "");
    }

    public RegexImpl8(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex8.class;
    }

}
