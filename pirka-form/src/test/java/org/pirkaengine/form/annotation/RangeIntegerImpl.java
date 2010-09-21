package org.pirkaengine.form.annotation;

import java.lang.annotation.Annotation;

@SuppressWarnings("all")
public class RangeIntegerImpl extends AbstractRangeAnnotationImpl<Integer> implements RangeInteger {

    public RangeIntegerImpl(int min, int max) {
        this(min, max, "");
    }

    public RangeIntegerImpl(int min, int max, String messageKey) {
        super(min, max, messageKey);
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return RangeInteger.class;
    }

    public final int max() {
        return max;
    }

    public final int min() {
        return min;
    }
}
