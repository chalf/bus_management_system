/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Users;
import com.busplanner.services.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/users")
    public String getUserList(Model model,
                              @RequestParam(value = "q", required = false) String query) {
        // Create a map to hold the parameters for the service method
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            params.put("q", query);  // Add the search query to the parameters
        }

        // Get the list of users
        List<Users> users = userService.getListUser(params);

        // Add attributes to the model
        model.addAttribute("users", users); 
        model.addAttribute("query", query);  

        return "userPage"; 
    }
}
