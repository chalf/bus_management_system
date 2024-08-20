/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.validator.beaninterface;

/**
 *
 * @author Admin
 */
import com.busplanner.validator.UniqueUsernameBeanValidator;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Admin
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueUsernameBeanValidator.class)
@Documented
public @interface UniqueUsername {

    String message() default "";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}