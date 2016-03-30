package com.theguardian.meddle;

import android.content.Context;

import com.theguardian.meddle.fields.EmailField;
import com.theguardian.meddle.fields.TextField;

/**
 * Created by max on 24/03/16.
 */
public class LoginForm extends Form {

    public final EmailField email = new EmailField();
    public final TextField password = new TextField();

    public LoginForm() {
        addField(email);
        addField(password);
    }

    @Override
    public void submit(Context context) {

    }

}
