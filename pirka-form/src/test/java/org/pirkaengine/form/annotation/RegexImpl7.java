package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl7 extends AbstractValuedAnnotationImpl implements Regex7 {

    public RegexImpl7(String value) {
        this(value, "");
    }

    public RegexImpl7(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex7.class;
    }

}
