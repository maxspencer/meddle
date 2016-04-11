package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.theguardian.meddle.validation.ValidationError;

/**
 * Created by max on 11/04/16.
 */
public abstract class BaseTextField extends Field<String> implements TextWatcher {

    private static final String VALUE_KEY = "value";
    private TextView textView;

    public BaseTextField() {
        super();
    }

    public BaseTextField(boolean required) {
        super(required);
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
