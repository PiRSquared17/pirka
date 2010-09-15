package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RequiredImpl extends AbstractAnnotationImpl implements Required {

    public RequiredImpl() {
        this("");
    }
    
    public RequiredImpl(String messageKey) {
        super(messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Required.class;
    }

}
