package com.theguardian.meddle.validation;

import android.content.Context;

import com.theguardian.meddle.R;

import java.util.regex.Pattern;

/**
 * Created by max on 05/04/16.
 */
public class RegexValidator implements Validator<CharSequence> {

    private final Pattern pattern;

    public RegexValidator(final Pattern pattern) {
        if (pattern == null) {
            throw new NullPointerException("pattern cannot be null");
        }
        this.pattern = pattern;
    }

    @Override
    public ValidationError validate(CharSequence field) {
        if (!pattern.matcher(field).matches()) {
            return new RegexError();
        }
        return null;
    }

    private class RegexError implements ValidationError {
        @Override
        public String getMessage(Context context) {
            return context.getString(R.string.error_regex);
        }

        @Override
        public String getMessageWithName(Context context, String fieldName) {
            return context.getString(R.string.error_regex_with_name, fieldName);
        }
    }

}
