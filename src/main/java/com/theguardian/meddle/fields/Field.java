package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.theguardian.meddle.validation.RequiredError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public abstract class Field<T>  {

    protected static final String VALUE_KEY = "value";

    private T value = null; // null implicit default for now
    private final boolean required;

    protected Field() {
        required = false;
    }

    protected Field(boolean required) {
        this.required = required;
    }

    public String getName() {
        return null;
    }

    public boolean isRequired() {
        return required;
    }

    public abstract boolean isEmpty();

    public abstract boolean isBound();

    public final T get() {
        return value;
    }

    public final void set(T value) {
        setWithoutWriteToView(value);
        if (isBound()) {
            writeValueToView(value);
        }
    }

    protected final void setWithoutWriteToView(T value) {
        this.value = value;
    }

    public final void bindTo(View view) {
        bindToImpl(view);
        writeValueToView(value);
    }

    protected abstract void bindToImpl(View view);

    protected abstract void writeValueToView(T value);

    public List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = new ArrayList<>();

        if (isRequired() && isEmpty()) {
            errors.add(new RequiredError());
        }

        return errors;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty();
    }

    public boolean validate() {
        final List<ValidationError> errors = getValidationErrors();
        if (errors.isEmpty()) {
            return true;
        } else if (errors.size() == 1) {
            showValidationError(errors.get(0));
            return false;
        } else {
            showValidationErrors(errors);
            return false;
        }
    }

    protected abstract void showValidationError(ValidationError error);

    protected void showValidationErrors(List<ValidationError> errors) {
        showValidationError(errors.get(0));
    }

    public abstract void saveState(@NonNull Bundle bundle);

    public abstract void restoreState(@NonNull Bundle bundle);

}
