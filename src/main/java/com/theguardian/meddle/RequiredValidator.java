package com.theguardian.meddle;

import android.content.Context;

import com.example.max.meddle.R;

/**
 * Created by max on 26/03/16.
 */
public class RequiredValidator implements Validator<Field<?>> {

    @Override
    public ValidationError validate(Field<?> field) {
        if (field.isRequired() && field.isEmpty()) {
            return new RequiredError(field);
        }

        return null;
    }

    private class RequiredError extends ValidationError {

        public RequiredError(Field field) {
            super(field);
        }

        @Override
        public String getErrorMessage(Context context) {
            return context.getString(R.string.required_error);
        }

    }
}
