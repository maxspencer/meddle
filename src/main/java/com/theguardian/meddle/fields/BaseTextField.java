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

    private TextView textView;

    public BaseTextField() {
        super();
    }

    public BaseTextField(boolean required) {
        super(required);
    }

    @Override
    public void bindViewImpl(View view) {
        if (!(view instanceof TextView)) {
            throw new ClassCastException("Can only bind to TextView");
        }
        textView = (TextView) view;
        textView.addTextChangedListener(this);
    }

    @Override
    public void unbindView() {
        textView.removeTextChangedListener(this);
        textView = null;
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
    protected String readFromView() {
        if (textView == null) {
            throw new IllegalStateException("No view bound to this field");
        }
        return textView.getText().toString();
    }

    @Override
    protected void writeToView(String value) {
        if (textView == null) {
            throw new IllegalStateException("No view bound to this field");
        }
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
        set(s.toString());
    }

}
