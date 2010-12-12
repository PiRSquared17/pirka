package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl9 extends AbstractValuedAnnotationImpl implements Regex9 {

    public RegexImpl9(String value) {
        this(value, "");
    }

    public RegexImpl9(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex9.class;
    }

}
