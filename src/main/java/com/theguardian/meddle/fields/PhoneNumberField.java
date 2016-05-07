package com.theguardian.meddle.fields;

import android.text.TextUtils;
import android.util.Patterns;

import com.theguardian.meddle.validation.InvalidError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.List;

/**
 * A {@link Field} which can be bound to {@link android.widget.EditText} views and accepts valid
 * phone numbers.
 */
public class PhoneNumberField extends BaseTextField {

    public PhoneNumberField() {
        super();
    }

    public PhoneNumberField(boolean required) {
        super(required);
    }

    @Override
    public List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = super.getValidationErrors();

        if (!TextUtils.isEmpty(get()) && !Patterns.PHONE.matcher(get()).matches()) {
            errors.add(new InvalidError());
        }

        return errors;
    }
}
