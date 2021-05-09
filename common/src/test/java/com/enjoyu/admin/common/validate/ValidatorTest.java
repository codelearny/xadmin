package com.enjoyu.admin.common.validate;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatorTest {
    @Test
    public void validatorFactory() {
        User user = new User();
        user.setId(12);
        user.setEmail("abc");
        user.setPhone("1333333");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        for (ConstraintViolation<User> userConstraintViolation : validate) {
            System.out.println(userConstraintViolation.getInvalidValue());
            System.out.println(userConstraintViolation.getMessage());
        }
        System.out.println(validate);
    }
}
