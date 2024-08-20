/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.pojo.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Admin
 */
public interface UserService extends UserDetailsService{
    Users retrieveUserByUsername(String username);
    boolean existsByUsername(String username);
    Users addUser(Users user);
    boolean authUser(String username, String password);
    Users getUserByUsername(String username);
    boolean existsByEmail(String email);
}
