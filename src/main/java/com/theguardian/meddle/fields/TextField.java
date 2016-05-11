package com.theguardian.meddle.fields;

import com.theguardian.meddle.validation.MinLengthValidator;

/**
 * Created by max on 24/03/16.
 */
public class TextField extends BaseTextField {

    public TextField() {
        super();
    }

    public TextField(boolean required) {
        super(required);
    }

    public TextField(boolean required, int minLength) {
        super(required);
        addValidator(new MinLengthValidator(minLength));
    }

}
