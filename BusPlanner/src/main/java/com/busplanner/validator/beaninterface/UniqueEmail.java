/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.validator.beaninterface;

import com.busplanner.validator.UniqueEmailBeanValidator;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Admin
 */
// kiểm tra email của user có trùng lặp hay không
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueEmailBeanValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}
