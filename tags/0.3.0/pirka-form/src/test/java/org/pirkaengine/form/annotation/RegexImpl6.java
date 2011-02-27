package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl6 extends AbstractValuedAnnotationImpl implements Regex6 {

    public RegexImpl6(String value) {
        this(value, "");
    }

    public RegexImpl6(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex6.class;
    }

}
