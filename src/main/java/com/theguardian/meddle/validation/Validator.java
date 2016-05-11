package com.theguardian.meddle.validation;

import android.support.annotation.Nullable;

/**
 * TODO
 */
public interface Validator<T> {

    @Nullable
    ValidationError getError(T value);

    boolean isValid(T value);

}
