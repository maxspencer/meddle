package com.theguardian.meddle;

import android.content.Context;

/**
 * Created by max on 25/03/16.
 */
public abstract class ValidationError {

    private final Field<?> field;

    protected ValidationError(Field<?> field) {
        if (field == null) {
            throw new NullPointerException("field cannot be null");
        }

        this.field = field;
    }

    public Field<?> getField() {
        return field;
    }

    public abstract String getErrorMessage(Context context);

}
