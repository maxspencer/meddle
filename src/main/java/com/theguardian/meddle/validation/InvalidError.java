package com.theguardian.meddle.validation;

import android.content.Context;
import android.support.annotation.StringRes;

import com.theguardian.meddle.R;

/**
 * Created by max on 07/04/16.
 */
public class InvalidError implements ValidationError {

    @StringRes private static final int DEFAULT_MESSAGE = R.string.error_invalid;
    @StringRes private static final int DEFAULT_WITH_NAME = R.string.error_invalid_with_name;

    @StringRes private final int message;
    @StringRes private final int withName;

    public InvalidError() {
        this.message = DEFAULT_MESSAGE;
        this.withName = DEFAULT_WITH_NAME;
    }

    public InvalidError(@StringRes int message) {
        this.message = message;
        this.withName = DEFAULT_WITH_NAME;
    }

    public InvalidError(@StringRes int message, @StringRes int messageWithNameFormat) {
        this.message = message;
        this.withName = messageWithNameFormat;
    }

    @Override
    public String getMessage(Context context) {
        return context.getString(message);
    }

    @Override
    public String getMessageWithName(Context context, String fieldName) {
        return context.getString(withName, fieldName);
    }

}
