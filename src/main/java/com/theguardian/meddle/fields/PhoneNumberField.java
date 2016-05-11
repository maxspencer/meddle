package com.theguardian.meddle.fields;

import com.theguardian.meddle.validation.RegexValidator;

/**
 * A {@link Field} which can be bound to {@link android.widget.EditText} views and accepts valid
 * phone numbers.
 */
public class PhoneNumberField extends BaseTextField {

    public PhoneNumberField() {
        super();
        addValidator(RegexValidator.PHONE);
    }

    public PhoneNumberField(boolean required) {
        super(required);
        addValidator(RegexValidator.PHONE);
    }

}
