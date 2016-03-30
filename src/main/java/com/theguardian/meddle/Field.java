package com.theguardian.meddle;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public abstract class Field<T>  {

    private T value = null; // null implicit default for now
    private final boolean isRequired;
    private final List<Validator<Field<T>>> validators = new ArrayList<>();

    protected Field(boolean required) {
        isRequired = required;
        if (required) {
            validators.add(new RequiredValidator());
        }
    }

    public String getName() {
        return null;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public abstract boolean isEmpty();

    public final T get() {
        return value;
    }

    public final void set(T value) {
        setWithoutWriteToView(value);
        writeValueToView(value);
    }

    public final void bindTo(View view) {
        bindToImpl(view);
        writeValueToView(value);
    }

    protected abstract void bindToImpl(View view);

    protected final void setWithoutWriteToView(T value) {
        this.value = value;
    }

    protected abstract void writeValueToView(T value);

    public List<ValidationError> getValidationErrors() {
        List<ValidationError> errors = new ArrayList<>();

        if (isRequired && isEmpty()) {
            // TODO
        }

        for (Validator<T> validator: validators) {
            ValidationError error = validator.validate(this);
            if (error != null) {
                errors.add(error);
            }
        }

        return errors;
    }

    public boolean isValid() {
        if (isRequired && isEmpty()) {
            return false;
        }

        for (Validator<T> validator: validators) {
            ValidationError error = validator.validate(this);
            if (error != null) {
                return false;
            }
        }
        return true;
    }

    public boolean validate() {
        List<ValidationError> errors = getValidationErrors();
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

}
