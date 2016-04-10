package com.theguardian.meddle.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.theguardian.meddle.validation.RegexError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public class EmailField extends TextField {

    public EmailField() {
        super();
    }

    public EmailField(boolean required) {
        super(required);
    }

    @Override
    public List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = super.getValidationErrors();

        if (!TextUtils.isEmpty(get()) && !Patterns.EMAIL_ADDRESS.matcher(get()).matches()) {
            errors.add(new RegexError());
        }

        return errors;
    }

    public static final Creator<EmailField> CREATOR = new Creator<EmailField>() {

        @Override
        public EmailField createFromParcel(Parcel source) {
            final EmailField field = new EmailField(source.readInt() == 1);
            field.setWithoutWriteToView(source.readString());
            return field;
        }

        @Override
        public EmailField[] newArray(int size) {
            return new EmailField[0];
        }

    };

}
