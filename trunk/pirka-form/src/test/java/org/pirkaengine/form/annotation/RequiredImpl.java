package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RequiredImpl extends AbstractAnnotationImpl implements Required {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Required.class;
    }

}
