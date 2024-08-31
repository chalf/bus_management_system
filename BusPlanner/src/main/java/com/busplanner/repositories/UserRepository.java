/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Users;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public interface UserRepository {
    Users retrieveUserByUsername(String username);
    boolean existsByUsername(String username);
    Users addUser(Users user);
    boolean authUser(String username, String password);
    Users getUserByUsername(String username);
    boolean existsByEmail(String email);
    List<Users> getListUser(Map<String, String> params);
    boolean updateUser(Users user);
    
}
