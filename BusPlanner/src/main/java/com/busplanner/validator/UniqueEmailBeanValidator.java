/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.validator;

import com.busplanner.services.UserService;
import com.busplanner.services.implement.UserServiceImplement;
import com.busplanner.validator.beaninterface.UniqueEmail;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class UniqueEmailBeanValidator implements ConstraintValidator<UniqueEmail, String>{
    @Autowired
    private UserService userService;

    
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cvc) {
        return !userService.existsByEmail(email);
    
    }
    
}
