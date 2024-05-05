package com.gdyd.gdydsupport.validator;

import com.gdyd.gdydsupport.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String VALID_EMIAL_REGEX = "^(.+)@(\\S+)$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(VALID_EMIAL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
