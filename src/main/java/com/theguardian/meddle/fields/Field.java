package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.theguardian.meddle.validation.RequiredError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by max on 24/03/16.
 */
public abstract class Field<T>  {

    public interface FieldValidityListener {

        void onValid(Field<?> field);

        void onInvalid(Field<?> field);

    }

    protected static final String VALUE_KEY = "value";

    private T value = null; // null implicit default for now
    private final boolean required;
    private boolean previouslyValid;
    private final Set<FieldValidityListener> validityListeners = new HashSet<>();

    protected Field() {
        required = false;
        previouslyValid = isValid();
    }

    protected Field(boolean required) {
        this.required = required;
        previouslyValid = isValid();
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

        final boolean nowValid = isValid();
        if (!validityListeners.isEmpty() && nowValid != previouslyValid) {
            for (FieldValidityListener listener : validityListeners) {
                if (nowValid) {
                    listener.onValid(this);
                } else {
                    listener.onInvalid(this);
                }
            }
        }
        previouslyValid = nowValid;
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

    public void addValidityListener(FieldValidityListener listener) {
        validityListeners.add(listener);
    }

    public void removeValidityListener(FieldValidityListener listener) {
        validityListeners.remove(listener);
    }

}
