/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Users;
import com.busplanner.repositories.UserRepository;
import com.busplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class UserServiceImplement implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users retrieveUser(int id) {
        return this.userRepository.retrieveUser(id);
    }
    
}
