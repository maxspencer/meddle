package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by max on 11/04/16.
 */
public class ChoiceField<T> extends Field<T> {

    private final List<T> choices = new ArrayList<>();
    private ChoiceBinding binding;

    public ChoiceField(List<T> choices) {
        super();
        this.choices.addAll(choices);
    }

    public ChoiceField(T[] choices) {
        super();
        Collections.addAll(this.choices, choices);
    }

    public ChoiceField(List<T> choices, boolean required) {
        super(required);
        this.choices.addAll(choices);
    }

    public ChoiceField(T[] choices, boolean required) {
        super(required);
        Collections.addAll(this.choices, choices);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isBound() {
        return false;
    }

    @Override
    protected void bindToImpl(View view) {
        binding = ChoiceBinding.getInstance(this, view);
    }

    @Override
    protected void writeValueToView(T value) {
        final int index = choices.indexOf(value);
        if (index < 0) { // Not in the list of choices
            throw new IllegalArgumentException("not a valid choice");
        }
        binding.writeValueToView(index);
    }

    @Override
    protected void showValidationError(ValidationError error) {

    }

    @Override
    public void saveState(@NonNull Bundle bundle) {

    }

    @Override
    public void restoreState(@NonNull Bundle bundle) {

    }

    private static abstract class ChoiceBinding {

        public static ChoiceBinding getInstance(ChoiceField field, View view) {
            if (view instanceof RadioGroup) {
                return new RadioChoiceBinding(field, (RadioGroup) view);
            }
            throw new ClassCastException("Can only bind to a RadioGroup view");
        }

        public abstract void writeValueToView(Integer value);
    }

    private static class RadioChoiceBinding extends ChoiceBinding {

        private final ChoiceField field;
        private final RadioGroup radioGroup;
        private final List<Integer> buttonIds = new ArrayList<>();

        public RadioChoiceBinding(ChoiceField field, RadioGroup radioGroup) {
            this.field = field;
            this.radioGroup = radioGroup;

            // Loop through the child views of the radio group and add the ids of all the radio
            // buttons to the buttonIds list. Then the id of the first radio button appears at
            // index 0, the second and index 1 and so on. Then buttonIds.get(i) gives the id of the
            // view corresponding to choice number i and buttonIds.indexOf(id) gives the choice
            // number of the view with id.
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                final View childView = radioGroup.getChildAt(i);
                if (childView instanceof RadioButton) {
                    buttonIds.add(childView.getId());
                }
            }
        }

        @Override
        public void writeValueToView(Integer value) {
            buttonIds.get(value);
        }
    }
}
