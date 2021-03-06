package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;

import com.theguardian.meddle.validation.ValidationError;

/**
 * Created by max on 11/04/16.
 */
public class BooleanField extends Field<Boolean> implements CompoundButton.OnCheckedChangeListener {

    private CompoundButton compoundButton = null;

    public BooleanField() {
        super(false);
    }

    public BooleanField(boolean required) {
        super(required, false);
    }

    @Override
    public boolean isEmpty() {
        return get() == null || !get();
    }

    @Override
    public boolean isBound() {
        return compoundButton != null;
    }

    @Override
    protected void bindViewImpl(View view) {
        if (!(view instanceof CompoundButton)) {
            throw new ClassCastException("BooleanField can only be bound to a CompoundButton");
        }
        compoundButton = (CompoundButton) view;
        compoundButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void unbindView() {
        compoundButton.setOnCheckedChangeListener(null);
        compoundButton = null;
    }

    @Override
    protected Boolean readFromView() {
        if (compoundButton == null) {
            throw new IllegalStateException("No view bound to this field");
        }
        return compoundButton.isChecked();
    }

    @Override
    protected void writeToView(Boolean value) {
        if (compoundButton == null) {
            throw new IllegalStateException("No view bound to this field");
        }
        compoundButton.setChecked(value != null && value);
    }

    @Override
    protected void showValidationError(ValidationError error) {
        compoundButton.setError(error.getMessage(compoundButton.getContext()));
    }

    @Override
    public void saveState(@NonNull Bundle bundle) {
        bundle.putBoolean(VALUE_KEY, get());
    }

    @Override
    public void restoreState(@NonNull Bundle bundle) {
        set(bundle.getBoolean(VALUE_KEY, false));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked != get()) {
            set(isChecked);
        }
        compoundButton.setError(null);
    }
}
