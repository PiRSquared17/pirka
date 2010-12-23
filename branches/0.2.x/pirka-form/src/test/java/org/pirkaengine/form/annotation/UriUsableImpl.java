package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class UriUsableImpl extends AbstractAnnotationImpl implements UriUsable {

    public UriUsableImpl() {
        this("");
    }
    
    public UriUsableImpl(String messageKey) {
        super(messageKey);
    }
    
    @Override
    public Class<? extends Annotation> annotationType() {
        return UriUsable.class;
    }

}
