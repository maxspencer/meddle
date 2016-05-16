package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.theguardian.meddle.validation.RequiredError;
import com.theguardian.meddle.validation.ValidationError;
import com.theguardian.meddle.validation.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by max on 24/03/16.
 */
public abstract class Field<T>  {

    public interface FieldValidityListener {

        void onValidityChanged(@NonNull Field<?> field, boolean valid);

    }

    protected static final String VALUE_KEY = "value";

    private T value = null; // null implicit default for now
    private final boolean required;
    private final List<Validator<? super T>> validators = new ArrayList<>();
    private boolean prevValid;
    private final Set<FieldValidityListener> validityListeners = new HashSet<>();

    protected Field() {
        this(false, null);
    }

    protected Field(@Nullable T defaultValue) {
        this(false, defaultValue);
    }

    protected Field(boolean required) {
        this(required, null);
    }

    protected Field(boolean required, @Nullable T value) {
        this.required = required;
        this.value = value;
        prevValid = isValid();
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
        this.value = value;

        final boolean nowValid = isValid();
        if (!validityListeners.isEmpty() && nowValid != prevValid) {
            for (FieldValidityListener listener : validityListeners) {
                listener.onValidityChanged(this, nowValid);
            }
        }
        prevValid = nowValid;

        if (isBound()) {
            if (!readFromView().equals(value)) {
                writeToView(value);
            }
        }
    }

    public final void bindView(View view) {
        bindViewImpl(view);
        writeToView(value);
    }

    protected abstract void bindViewImpl(View view);

    public abstract void unbindView();

    /**
     * Read the value currently displayed by the view bound to this field.
     *
     * @return the value the bound field is currently displaying
     * @throws IllegalStateException if no view is bound
     */
    protected abstract T readFromView();

    /**
     * Write a value to the view bound to this field.
     *
     * @param value for the bound view to display
     * @throws IllegalStateException if no view is bound
     */
    protected abstract void writeToView(T value);

    protected void addValidator(Validator<? super T> validator) {
        validators.add(validator);
    }

    public final List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = new ArrayList<>();

        if (isRequired() && isEmpty()) {
            errors.add(new RequiredError());
        }

        if (!isEmpty()) {
            for (Validator<? super T> validator : validators) {
                final ValidationError error = validator.getError(value);
                if (error != null) {
                    errors.add(error);
                }
            }
        }

        return errors;
    }

    public final boolean isValid() {
        if (isRequired() && isEmpty()) {
            return false;
        }

        if (!isEmpty()) {
            for (Validator<? super T> validator: validators) {
                if (!validator.isValid(value)) {
                    return false;
                }
            }
        }

        return true;
    }

    public final boolean validate() {
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

    public void addValidityListener(FieldValidityListener listener) {
        validityListeners.add(listener);
    }

    public void removeValidityListener(FieldValidityListener listener) {
        validityListeners.remove(listener);
    }

}
