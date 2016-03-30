package com.theguardian.meddle;

import android.content.Context;

import com.example.max.meddle.R;

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
            return new MinLengthError(field, minLength);
        }

        return null;
    }

    /**
     * Created by max on 26/03/16.
     */
    public static class MinLengthError extends ValidationError {

        private final int minLength;

        public MinLengthError(Field<CharSequence> field, int minLength) {
            super(field);
            this.minLength = minLength;
        }

        @Override
        public String getErrorMessage(Context context) {
            return context.getString(R.string.min_length_error, getField().getName(), minLength);
        }
    }

}
