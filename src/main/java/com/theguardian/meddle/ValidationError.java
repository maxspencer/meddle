package com.theguardian.meddle;

import android.content.Context;

/**
 * Created by max on 25/03/16.
 */
public interface ValidationError {

    String getMessage(Context context);

    String getMessageWithName(Context context, String fieldName);

}
