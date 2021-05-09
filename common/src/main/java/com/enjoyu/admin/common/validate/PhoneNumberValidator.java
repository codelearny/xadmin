package com.enjoyu.admin.common.validate;

import com.enjoyu.admin.common.validate.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, CharSequence> {
    private PhoneNumber ann;
    private Pattern pattern;
    private static final Pattern PATTERN_DEFAULT = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9]))\\d{8}$+");

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        this.ann = constraintAnnotation;
        this.pattern = Optional.of(constraintAnnotation)
                .map(PhoneNumber::regexp)
                .filter(s -> s.length() > 0)
                .map(Pattern::compile)
                .orElse(PATTERN_DEFAULT);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
