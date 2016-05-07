package com.theguardian.meddle.validation;

import android.content.Context;

import com.theguardian.meddle.R;

/**
 * A {@link ValidationError} returned when a field requires a value but none is given.
 */
public class RequiredError implements ValidationError {

    @Override
    public String getMessage(Context context) {
        return context.getString(R.string.error_required);
    }

    @Override
    public String getMessageWithName(Context context, String fieldName) {
        return context.getString(R.string.error_required_with_name, fieldName);
    }
}
