package com.theguardian.meddle.fields;

import android.text.TextUtils;
import android.util.Patterns;

import com.theguardian.meddle.validation.RegexError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public class EmailField extends BaseTextField {

    public EmailField() {
        super();
    }

    public EmailField(boolean required) {
        super(required);
    }

    @Override
    public List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = super.getValidationErrors();

        if (!TextUtils.isEmpty(get()) && !Patterns.EMAIL_ADDRESS.matcher(get()).matches()) {
            errors.add(new RegexError());
        }

        return errors;
    }

}
