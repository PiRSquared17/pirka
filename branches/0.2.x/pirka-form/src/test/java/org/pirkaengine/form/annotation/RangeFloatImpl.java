package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RangeFloatImpl extends AbstractRangeAnnotationImpl<Float> implements RangeFloat {

    public RangeFloatImpl(float min, float max) {
        this(min, max, "");
    }

    public RangeFloatImpl(float min, float max, String messageKey) {
        super(min, max, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return RangeFloat.class;
    }

    public final float max() {
        return max;
    }

    public final float min() {
        return min;
    }
}
