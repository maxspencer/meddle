package com.theguardian.meddle.validation;

import android.content.Context;

import com.theguardian.meddle.R;

/**
 * Created by max on 30/03/16.
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
