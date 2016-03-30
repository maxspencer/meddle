package com.theguardian.meddle;

import android.view.View;

import com.theguardian.meddle.fields.Field;
import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by max on 24/03/16.
 */
public abstract class Form {

    private List<Field<?>> fields = new ArrayList<>();

    public Form() {

    }

    public void addField(Field<?> field) {
        fields.add(field);
    }

    public void bindTo(List<View> views) {
        if (views.size() != fields.size()) {
            throw new IllegalArgumentException("Wrong number of views to bind");
        }

        for (int i = 0; i < views.size(); i++) {
            fields.get(i).bindTo(views.get(i));
        }
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
