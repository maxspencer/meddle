package com.theguardian.meddle.fields;

import com.theguardian.meddle.validation.ValidationError;

/**
 * Created by max on 18/04/16.
 */
public interface ViewBinder<T> {
    void writeToView(T value);
    void showValidationError(ValidationError error);
    T readFromView();
}
