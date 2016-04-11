package com.theguardian.meddle.fields;

import com.theguardian.meddle.validation.MinLengthError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public class TextField extends BaseTextField {

    private final int minLength;

    public TextField() {
        super();
        minLength = 0;
    }

    public TextField(boolean required) {
        super(required);
        minLength = 0;
    }

    public TextField(boolean required, int minLength) {
        super(required);
        this.minLength = minLength;
    }

    @Override
    public List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = super.getValidationErrors();

        if (get().length() < minLength) {
            errors.add(new MinLengthError(minLength));
        }

        return errors;
    }

}
