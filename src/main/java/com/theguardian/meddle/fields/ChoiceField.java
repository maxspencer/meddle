package com.theguardian.meddle.fields;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.theguardian.meddle.validation.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by max on 11/04/16.
 */
public class ChoiceField<T> extends Field<T> {

    private final List<T> choices = new ArrayList<>();
    private ViewBinder<Integer> binding;

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
        return get() == null;
    }

    @Override
    public boolean isBound() {
        return binding != null;
    }

    @Override
    protected void bindToImpl(View view) {
        if (view instanceof RadioGroup) {
            binding = new RadioChoiceBinding<>(this, (RadioGroup) view);
            return;
        }
        throw new ClassCastException("Can only bind to a RadioGroup view");
    }

    @Override
    protected void writeValueToView(T value) {
        if (value == null) {
            return;
        }

        final int index = choices.indexOf(value);
        if (index < 0) { // Not in the list of choices
            throw new IllegalArgumentException("not a valid choice");
        }
        binding.writeValueToView(index);
    }

    @Override
    protected void showValidationError(ValidationError error) {
        binding.showValidationError(error);
    }

    @Override
    public void saveState(@NonNull Bundle bundle) {
        // TODO
    }

    @Override
    public void restoreState(@NonNull Bundle bundle) {
        // TODO
    }

    private static class RadioChoiceBinding<T>
            implements ViewBinder<Integer>, RadioGroup.OnCheckedChangeListener {

        private final ChoiceField<T> field;
        private final RadioGroup radioGroup;
        private final List<Integer> buttonIds = new ArrayList<>();

        public RadioChoiceBinding(ChoiceField<T> field, RadioGroup radioGroup) {
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

            // Check there are the correct number of RadioButtons
            if (buttonIds.size() != field.choices.size()) {
                throw new IllegalArgumentException(
                        "Incorrect number of RadioButtons to bind: expecting " +
                                field.choices.size() +
                                " got " +
                                buttonIds.size()
                );
            }

            radioGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void writeValueToView(Integer value) {
            radioGroup.check(buttonIds.get(value));
        }

        @Override
        public void showValidationError(ValidationError error) {
            ((RadioButton) radioGroup.findViewById(buttonIds.get(0))).setError(error.getMessage(radioGroup.getContext()));
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            final int choiceIndex = buttonIds.indexOf(checkedId);
            field.set(field.choices.get(choiceIndex));
        }

    }

}
