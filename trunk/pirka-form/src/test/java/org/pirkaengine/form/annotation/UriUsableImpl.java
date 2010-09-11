package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class UriUsableImpl extends AbstractAnnotationImpl implements UriUsable {

    @Override
    public Class<? extends Annotation> annotationType() {
        return UriUsable.class;
    }

}
