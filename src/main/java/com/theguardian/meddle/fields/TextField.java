package com.theguardian.meddle.fields;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.theguardian.meddle.validation.MinLengthError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public class TextField extends Field<String> implements TextWatcher, Parcelable {

    private final int minLength;
    private TextView textView;

    public TextField() {
        super();
        minLength = 0;
    }

    public TextField(boolean required) {
        super(required);
        minLength = 0;
    }

    public TextField(boolean required, int minLength) {
        super(required);
        this.minLength = minLength;
    }

    public static final Creator<TextField> CREATOR = new Creator<TextField>() {

        @Override
        public TextField createFromParcel(Parcel in) {
            final TextField field = new TextField(in.readInt() == 1, in.readInt());
            field.setWithoutWriteToView(in.readString());
            return field;
        }

        @Override
        public TextField[] newArray(int size) {
            return new TextField[size];
        }

    };

    @Override
    public void bindToImpl(View view) {
        if (!(view instanceof TextView)) {
            throw new ClassCastException("Can only bind to TextView");
        }
        textView = (TextView) view;
        textView.addTextChangedListener(this);
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(get());
    }

    @Override
    public boolean isBound() {
        return textView != null;
    }

    @Override
    protected void writeValueToView(String value) {
        textView.setText(value);
    }

    @Override
    public List<ValidationError> getValidationErrors() {
        final List<ValidationError> errors = super.getValidationErrors();

        if (get().length() < minLength) {
            errors.add(new MinLengthError(minLength));
        }

        return errors;
    }

    @Override
    protected void showValidationError(ValidationError error) {
        textView.setError(error.getMessage(textView.getContext()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Unused
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Unused
    }

    @Override
    public void afterTextChanged(Editable s) {
        setWithoutWriteToView(s.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isRequired() ? 1 : 0);
        dest.writeInt(minLength);
        dest.writeString(get());
    }
}
