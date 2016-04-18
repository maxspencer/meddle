package com.theguardian.meddle.fields;

import com.theguardian.meddle.validation.ValidationError;

/**
 * Created by max on 18/04/16.
 */
public interface ViewBinder<T> {
    void writeValueToView(T value);
    void showValidationError(ValidationError error);
}
