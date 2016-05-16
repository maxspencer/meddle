package com.theguardian.meddle.validation;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * TODO
 */
public class RegexValidator extends Validator<CharSequence> {

    public static final RegexValidator EMAIL = new RegexValidator(Patterns.EMAIL_ADDRESS);
    public static final RegexValidator PHONE = new RegexValidator(Patterns.PHONE);

    @NonNull private final Pattern pattern;

    public RegexValidator(@NonNull Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    protected ValidationError newError(CharSequence value) {
        return new ValidationError();
    }

    @Override
    public boolean isValid(CharSequence value) {
        return !TextUtils.isEmpty(value) && pattern.matcher(value).matches();
    }

}
