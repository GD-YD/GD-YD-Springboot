package com.gdyd.gdydsupport.validator;

import com.gdyd.gdydsupport.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    /**
     * <pre>
     * password must contain 1 number (0-9)
     * password must contain 1 uppercase letters
     * password must contain 1 lowercase letters
     * password must contain 1 non-alpha numeric number
     * password is 8-16 characters with no space
     * </pre>
     */

    private static final String VALID_PASSWORD_REGEX = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\s:])(\\S){8,16}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile(VALID_PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
