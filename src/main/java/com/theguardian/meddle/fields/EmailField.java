package com.theguardian.meddle.fields;

import com.theguardian.meddle.validation.RegexValidator;

/**
 * A {@link Field} which can be bound to {@link android.widget.EditText} views and accepts valid
 * email addresses.
 */
public class EmailField extends BaseTextField {

    public EmailField() {
        super();
        addValidator(RegexValidator.EMAIL);
    }

    public EmailField(boolean required) {
        super(required);
        addValidator(RegexValidator.EMAIL);
    }

}
