package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RegexImpl4 extends AbstractValuedAnnotationImpl implements Regex4 {

    public RegexImpl4(String value) {
        this(value, "");
    }

    public RegexImpl4(String value, String messageKey) {
        super(value, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Regex4.class;
    }

}
