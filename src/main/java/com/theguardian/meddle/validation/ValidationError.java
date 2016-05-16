package com.theguardian.meddle.validation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.theguardian.meddle.R;

/**
 * Base class for all errors returned when validating a {@link com.theguardian.meddle.fields.Field}.
 */
public class ValidationError {

    @StringRes private final int message;
    @StringRes private final int messageWithName;

    public ValidationError() {
        this.message = R.string.error_invalid;
        this.messageWithName = R.string.error_invalid_with_name;
    }

    public ValidationError(@StringRes int message, @StringRes int messageWithName) {
        this.message = message;
        this.messageWithName = messageWithName;
    }

    /**
     * Returns a String describing this error without the name of the invalid field. For example
     * "Must contain 6 characters of more", not "Password must contain 6 characters or more"
     *
     * @param context to use to create the String
     * @return the String describing this error
     */
    @NonNull
    public String getMessage(@NonNull Context context) {
        return context.getString(message);
    }

    /**
     * Returns a String describing this error with the name of the invalid field.
     *
     * @param context to use to create the String
     * @param fieldName the name of the field to include
     * @return the String describing this error
     */
    @NonNull
    public String getMessageWithName(@NonNull Context context, @NonNull String fieldName) {
        return context.getString(messageWithName, fieldName);
    }

}
