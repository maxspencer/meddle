package com.theguardian.meddle.validation;

import android.content.Context;

import com.theguardian.meddle.R;

/**
 * Created by max on 07/04/16.
 */
public class RegexError implements ValidationError {

    @Override
    public String getMessage(Context context) {
        return context.getString(R.string.error_regex);
    }

    @Override
    public String getMessageWithName(Context context, String fieldName) {
        return context.getString(R.string.error_regex_with_name, fieldName);
    }

}
