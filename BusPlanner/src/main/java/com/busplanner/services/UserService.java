/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.dto.AddUserDto;
import com.busplanner.dto.UpdateUserDto;
import com.busplanner.pojo.Users;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{
    Users retrieveUserByUsername(String username);
    boolean existsByUsername(String username);
    AddUserDto addUser(Users user);
    boolean authUser(String username, String password);
    Users getUserByUsername(String username);
    boolean existsByEmail(String email);
    List<Users> getListUser(Map<String, String> params);
    boolean updateUser(UpdateUserDto userData);
}
