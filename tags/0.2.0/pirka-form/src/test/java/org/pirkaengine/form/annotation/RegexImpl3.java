package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl3 extends AbstractValuedAnnotationImpl implements Regex3 {

    public RegexImpl3(String value) {
        this(value, "");
    }

    public RegexImpl3(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex3.class;
    }

}
