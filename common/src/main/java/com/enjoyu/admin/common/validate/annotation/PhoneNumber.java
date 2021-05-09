package com.enjoyu.admin.common.validate.annotation;

import com.enjoyu.admin.common.validate.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "phone number is invalid";

    Class<?>[] groups() default {};

    /**
     * @return an additional regular expression the annotated element must match.
     * default is null
     */
    String regexp() default "";

    Class<? extends Payload>[] payload() default {};

}
