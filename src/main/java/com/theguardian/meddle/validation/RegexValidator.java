package com.theguardian.meddle.validation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * TODO
 */
public enum RegexValidator implements Validator<CharSequence> {

    EMAIL(Patterns.EMAIL_ADDRESS),
    PHONE(Patterns.PHONE);

    @NonNull private final Validator<CharSequence> validator;

    RegexValidator(@NonNull final Pattern pattern) {
        validator = new BaseValidator<CharSequence>() {
            @Override
            protected ValidationError newError(CharSequence value) {
                return new ValidationError();
            }

            @Override
            public boolean isValid(CharSequence value) {
                return !TextUtils.isEmpty(value) && pattern.matcher(value).matches();
            }
        };
    }

    @Nullable
    @Override
    public ValidationError getError(CharSequence value) {
        return validator.getError(value);
    }

    @Override
    public boolean isValid(CharSequence value) {
        return validator.isValid(value);
    }

}
