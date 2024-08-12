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

    @RequestMapping("/users/{id}")
    public String getUserDetail(Model model, @PathVariable(value = "id") String id) {
        model.addAttribute("user", this.userService.retrieveUser(Integer.parseInt(id)));
        return "users";
    }
}
