package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl2 extends AbstractValuedAnnotationImpl implements Regex2 {

    public RegexImpl2(String value) {
        this(value, "");
    }

    public RegexImpl2(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex2.class;
    }

}
