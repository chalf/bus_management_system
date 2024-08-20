/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.validator;

import com.busplanner.services.UserService;
import com.busplanner.services.implement.UserServiceImplement;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.busplanner.validator.beaninterface.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Admin
 */
public class UniqueUsernameBeanValidator implements ConstraintValidator<UniqueUsername, String>{
    @Autowired
    private UserService userService;
    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userService.existsByUsername(username);
        // nếu existsByUsername() -> true -> có tồn tại -> !existsByUsername() là false -> vi phạm
    }
    
}
