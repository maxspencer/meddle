package com.theguardian.meddle;

import android.view.View;

import com.theguardian.meddle.fields.Field;
import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by max on 24/03/16.
 */
public abstract class Form {

    private List<Field<?>> fields = new ArrayList<>();
    private boolean isBound = false;

    public Form() {

    }

    public void addField(Field<?> field) {
        if (isBound) {
            throw new IllegalStateException("Cannot add fields to form after it has been bound");
        }
        fields.add(field);
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

        isBound = true;
    }

    public void bindTo(View... views) {
        bindTo(Arrays.asList(views));
    }

    public boolean isBound() {
        return isBound;
    }

    public Map<Field<?>, List<ValidationError>> getValidationErrors() {
        final Map<Field<?>, List<ValidationError>> errors = new HashMap<>();

        for (Field<?> field: fields) {
            final List<ValidationError> fieldErrors = field.getValidationErrors();
            if (!fieldErrors.isEmpty()) {
                errors.put(field, fieldErrors);
            }
        }

        return errors;
    }

    public boolean isValid() {
        for (Field<?> field: fields) {
            if (!field.isValid()) {
                return false;
            }
        }

        return true;
    }

    public boolean validate() {
        boolean valid = true;

        for (Field<?> field: fields) {
            final boolean fieldValid = field.validate();
            valid &= fieldValid;
        }

        return valid;
    }

}
