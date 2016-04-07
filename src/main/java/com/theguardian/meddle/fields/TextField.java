package com.theguardian.meddle.fields;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.theguardian.meddle.validation.MinLengthValidator;
import com.theguardian.meddle.validation.ValidationError;
import com.theguardian.meddle.validation.Validator;

import java.util.Collections;
import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public class TextField extends Field<CharSequence> implements TextWatcher {

    private TextView textView;

    public TextField() {
        super();
    }

    public TextField(boolean required) {
        super(required);
    }

    public TextField(boolean required, int minLength) {
        super(required, Collections.singletonList(new MinLengthValidator(minLength)));
    }

    public TextField(boolean required, int minLength, @Nullable List<? extends Validator<CharSequence>> validators) {
        super(required, validators);
    }

    @Override
    public void bindToImpl(View view) {
        if (!(view instanceof TextView)) {
            throw new ClassCastException("can only bind to TextView");
        }
        textView = (TextView) view;
        textView.addTextChangedListener(this);
    }

    @Override
    protected void writeValueToView(CharSequence value) {
        textView.setText(value);
    }

    @Override
    public boolean isEmpty() {
        return TextUtils.isEmpty(get());
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
    protected void showValidationError(ValidationError error) {
        textView.setError(error.getMessage(textView.getContext()));
    }

}
