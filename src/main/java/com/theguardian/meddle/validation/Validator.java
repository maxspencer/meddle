package com.theguardian.meddle.validation;

import android.support.annotation.Nullable;

/**
 * TODO
 */
public abstract class Validator<T> {

    @Nullable
    public final ValidationError getError(T value) {
        if (!isValid(value)) {
            return newError(value);
        } else {
            return null;
        }
    }

    protected abstract ValidationError newError(T value);

    public abstract boolean isValid(T value);

}
