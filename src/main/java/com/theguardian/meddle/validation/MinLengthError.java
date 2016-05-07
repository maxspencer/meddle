package com.theguardian.meddle.validation;

import android.content.Context;

import com.theguardian.meddle.R;

/**
 * A {@link ValidationError} returned when the input a field is too short.
 */
public class MinLengthError implements ValidationError {

    private final int minLength;

    public MinLengthError(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public String getMessage(Context context) {
        return context.getString(R.string.error_min_length, minLength);
    }

    @Override
    public String getMessageWithName(Context context, String fieldName) {
        return context.getString(R.string.error_min_length_with_name, fieldName, minLength);
    }
}
