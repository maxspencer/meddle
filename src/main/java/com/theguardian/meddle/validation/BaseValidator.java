package com.theguardian.meddle.validation;

import android.support.annotation.Nullable;

/**
 * TODO
 */
public abstract class BaseValidator<T> implements Validator<T> {

    @Override
    @Nullable
    public final ValidationError getError(T value) {
        if (!isValid(value)) {
            return newError(value);
        } else {
            return null;
        }
    }

    protected abstract ValidationError newError(T value);

}
