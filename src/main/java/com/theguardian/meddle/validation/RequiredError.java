package com.theguardian.meddle.validation;

import com.theguardian.meddle.R;

/**
 * A {@link ValidationError} returned when a field requires a value but none is given.
 */
public class RequiredError extends ValidationError {

    public RequiredError() {
        super(R.string.error_required, R.string.error_required_with_name);
    }

}
