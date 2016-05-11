package com.theguardian.meddle.validation;

import android.text.TextUtils;

/**
 * TODO
 */
public class MinLengthValidator extends BaseValidator<CharSequence> {

    private final int minLength;

    public MinLengthValidator(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean isValid(CharSequence value) {
        return TextUtils.isEmpty(value) && value.length() >= minLength;
    }

    @Override
    protected ValidationError newError(CharSequence value) {
        return new MinLengthError(minLength);
    }

}
