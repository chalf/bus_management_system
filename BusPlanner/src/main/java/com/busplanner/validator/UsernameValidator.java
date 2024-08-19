/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.validator;

import com.busplanner.pojo.Users;
import com.busplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Admin
 */
// Kiểm tra username được truyền đã tồn tại hay chưa
@Component
public class UsernameValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Users user = (Users) target;
        if (this.userService.existsByUsername(user.getUsername())) {
            errors.rejectValue("username", "validation.user.username.exists");
        }

    }

}
