package com.theguardian.meddle.validation;

/**
 * Created by max on 25/03/16.
 */
public interface Validator<T> {

    ValidationError validate(T field);

}
