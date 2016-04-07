package com.theguardian.meddle.fields;

import android.support.annotation.Nullable;
import android.view.View;

import com.theguardian.meddle.validation.RequiredValidator;
import com.theguardian.meddle.validation.ValidationError;
import com.theguardian.meddle.validation.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public abstract class Field<T>  {

    private T value = null; // null implicit default for now
    private final boolean isRequired;
    private final RequiredValidator requiredValidator = new RequiredValidator();
    private final List<Validator<T>> validators = new ArrayList<>();

    protected Field() {
        this(false, null);
    }

    protected Field(boolean required) {
        this(required, null);
    }

    protected Field(boolean required, @Nullable List<? extends Validator<T>> validators) {
        isRequired = required;
        if (validators != null) {
            this.validators.addAll(validators);
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
        final List<ValidationError> errors = new ArrayList<>();

        addIfNotNull(errors, requiredValidator.validate(this));

        for (Validator<T> validator: validators) {
            addIfNotNull(errors, validator.validate(value));
        }

        return errors;
    }

    public boolean isValid() {
        if (!isValid(requiredValidator, this)) {
            return false;
        }

        for (Validator<T> validator: validators) {
            if (!isValid(validator, value)) {
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

    private static <T> boolean isValid(Validator<T> validator, T value) {
        return validator.validate(value) != null;
    }

    private static <T> void addIfNotNull(Collection<T> collection, T item) {
        if (item != null) {
            collection.add(item);
        }
    }

}
