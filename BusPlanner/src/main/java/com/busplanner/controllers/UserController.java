/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Users;
import com.busplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@Controller

public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users/{username}")
    public String getUserDetail(Model model, @PathVariable(value = "username") String username) {
        model.addAttribute("user", this.userService.retrieveUserByUsername(username));

        return "users";
    }
}
