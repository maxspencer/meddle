package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.theguardian.meddle.validation.MinLengthError;
import com.theguardian.meddle.validation.ValidationError;

import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public class TextField extends Field<String> implements TextWatcher {

    private static final String VALUE_KEY = "value";

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
    public void saveState(@NonNull Bundle bundle) {
        bundle.putString(VALUE_KEY, get());
    }

    @Override
    public void restoreState(@NonNull Bundle bundle) {
        set(bundle.getString(VALUE_KEY, null));
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

}
