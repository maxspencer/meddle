package com.theguardian.meddle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.theguardian.meddle.fields.Field;
import com.theguardian.meddle.util.FormatString;
import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by max on 24/03/16.
 */
public abstract class Form {

    public interface FormValidityListener {

        void onValidityChanged(@NonNull Form form, boolean valid);

    }

    private static final FormatString FIELD_KEY = new FormatString("field%d");

    private final List<Field<?>> fields = new ArrayList<>();

    private final Set<Field<?>> invalidFields = new HashSet<>();
    private final Set<FormValidityListener> formValidityListeners = new HashSet<>();
    private final Field.FieldValidityListener fieldValidityListener = new Field.FieldValidityListener() {
        @Override
        public void onValidityChanged(@NonNull Field<?> field, boolean valid) {
            if (valid) {
                if (invalidFields.remove(field) && invalidFields.isEmpty()) {
                    // field was removed and invalidFields is now empty so we are transitioning
                    // from the form being invalid to being valid
                    for (FormValidityListener listener: formValidityListeners) {
                        listener.onValidityChanged(Form.this, true);
                    }
                }
            } else {
                final boolean wasEmpty = invalidFields.isEmpty();
                invalidFields.add(field);
                if (wasEmpty) {
                    // invalidFields was empty before, but now contains one invalid field so we
                    // are transitioning from the form being valid to being invalid
                    for (FormValidityListener listener: formValidityListeners) {
                        listener.onValidityChanged(Form.this, false);
                    }
                }
            }
        }
    };

    public Form() {

    }

    protected <T extends Field<?>> T addField(T field) {
        fields.add(field);
        if (!field.isValid()) {
            invalidFields.add(field);
        }
        field.addValidityListener(fieldValidityListener);
        return field;
    }

    public void bindTo(List<View> views) {
        if (views == null) {
            throw new NullPointerException("views cannot be null");
        }
        if (fields.size() != views.size()) {
            throw new IllegalArgumentException(
                    "Incorrect number of views to bind: expecting " +
                            fields.size() +
                            " got " +
                            views.size()
            );
        }

        for (int i = 0; i < views.size(); i++) {
            fields.get(i).bindTo(views.get(i));
        }
    }

    public void bindTo(View... views) {
        bindTo(Arrays.asList(views));
    }

    public Map<Field<?>, List<ValidationError>> getValidationErrors() {
        final Map<Field<?>, List<ValidationError>> errors = new HashMap<>();

        for (Field<?> field: invalidFields) {
            errors.put(field, field.getValidationErrors());
        }

        return errors;
    }

    public boolean isValid() {
        return invalidFields.isEmpty();
    }

    public boolean validate() {
        boolean valid = true;

        for (Field<?> field: fields) {
            final boolean fieldValid = field.validate();
            valid &= fieldValid;
        }

        return valid;
    }

    public void saveState(@NonNull Bundle bundle) {
        for (int i = 0; i < fields.size(); i++) {
            final Bundle fieldBundle = new Bundle();
            fields.get(i).saveState(fieldBundle);
            bundle.putBundle(FIELD_KEY.format(i), fieldBundle);
        }
    }

    public void restoreState(@Nullable Bundle bundle) {
        if (bundle == null) {
            return;
        }

        for (int i = 0; i < fields.size(); i++) {
            final Bundle fieldBundle = bundle.getBundle(FIELD_KEY.format(i));
            if (fieldBundle != null) {
                fields.get(i).restoreState(fieldBundle);
            }
        }
    }

    public void addValidityListener(FormValidityListener listener) {
        formValidityListeners.add(listener);
    }

    public void removeValidityListener(FormValidityListener listener) {
        formValidityListeners.remove(listener);
    }

}
