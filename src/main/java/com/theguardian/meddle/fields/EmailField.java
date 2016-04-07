package com.theguardian.meddle.fields;

import android.os.Parcelable;
import android.util.Log;

/**
 * Created by max on 24/03/16.
 */
public class EmailField extends TextField implements Parcelable {

    public EmailField() {
        super();
        Log.d("parcel", "somehow created EmailField");
    }

    public EmailField(boolean required) {
        super(required);
        Log.d("parcel", "somehow created EmailField");
    }
}
