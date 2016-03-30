package com.theguardian.meddle;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 24/03/16.
 */
public abstract class Form {

    private List<Field<?>> fields = new ArrayList<>();

    public Form() {

    }

    public void addField(Field<?> field) {
        fields.add(field);
    }

    public void bindTo(List<View> views) {
        if (views.size() != fields.size()) {
            throw new IllegalArgumentException("Wrong number of views to bind");
        }

        for (int i = 0; i < views.size(); i++) {
            fields.get(i).bindTo(views.get(i));
        }
    }

    public abstract void submit(Context context);

}
