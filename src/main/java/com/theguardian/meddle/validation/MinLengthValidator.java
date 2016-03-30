package com.theguardian.meddle.validation;

import android.content.Context;

import com.theguardian.meddle.R;
import com.theguardian.meddle.fields.Field;

/**
 * Created by max on 26/03/16.
 */
public class MinLengthValidator implements Validator<Field<CharSequence>> {

    private final int minLength;

    public MinLengthValidator(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public ValidationError validate(Field<CharSequence> field) {
        final int length = (field.get() != null) ? field.get().length() : 0;
        if (length < minLength) {
            return new MinLengthError(minLength);
        }

        return null;
    }

    /**
     * Created by max on 26/03/16.
     */
    public static class MinLengthError implements ValidationError {

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

}
