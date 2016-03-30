package com.theguardian.meddle;

import android.content.Context;

/**
 * Created by max on 26/03/16.
 */
public class RequiredValidator implements Validator<Field<?>> {

    @Override
    public ValidationError validate(Field<?> field) {
        if (field.isRequired() && field.isEmpty()) {
            return new RequiredError();
        }

        return null;
    }

    /**
     * Created by max on 30/03/16.
     */
    public static class RequiredError implements ValidationError {

        @Override
        public String getMessage(Context context) {
            return context.getString(R.string.error_required);
        }

        @Override
        public String getMessageWithName(Context context, String fieldName) {
            return context.getString(R.string.error_required_with_name, fieldName);
        }
    }
}
