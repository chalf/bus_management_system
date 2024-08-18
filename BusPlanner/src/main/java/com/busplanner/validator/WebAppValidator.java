/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.validator;

/**
 *
 * @author Admin
 */
import com.busplanner.pojo.Users;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WebAppValidator implements Validator {

    @Autowired
    private javax.validation.Validator beanValidator;
    
    private Set<Validator> springValidators = new HashSet<>();

    @Override
    public boolean supports(Class<?> clazz) {
        return Users.class.isAssignableFrom(clazz);
    //nếu muốn hỗ trợ class khác thì thêm: || <Class>.class…..(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> constraintViolations = beanValidator.validate(target);
        for (ConstraintViolation<Object> obj : constraintViolations) {
            errors.rejectValue(
                    //chỉ định tên thuộc tính của Object
                    obj.getPropertyPath().toString(),
                //message template là message được khai báo trong pojo của Object
                    obj.getMessageTemplate(),
            //còn message này là message mặc định mà được khai báo trong pojo (nếu có)
                    obj.getMessage());
        }
        
        //sau khi đã validate bằng BeanValidation, thì mới validate bằng SpringValidation
        for (Validator obj : springValidators) {
            obj.validate(target, errors);
        }
    }
    
    // Setter để thêm các validator của Spring trong 1 method ở WebApplicationContext.java
    public void setSpringValidators(Set<Validator> springValidators) {
        this.springValidators = springValidators;
    }
}

