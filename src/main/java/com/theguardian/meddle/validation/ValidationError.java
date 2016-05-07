package com.theguardian.meddle.validation;

import android.content.Context;

/**
 * Interface for all errors returned when validating a {@link com.theguardian.meddle.fields.Field}.
 */
public interface ValidationError {

    /**
     * Returns a String describing this error without the name of the invalid field. For example
     * "Must contain 6 characters of more", not "Password must contain 6 characters or more"
     *
     * @param context to use to create the String
     * @return the String describing this error
     */
    String getMessage(Context context);

    /**
     * Returns a String describing this error with the name of the invalid field.
     *
     * @param context to use to create the String
     * @param fieldName the name of the field to include
     * @return the String describing this error
     */
    String getMessageWithName(Context context, String fieldName);

}
