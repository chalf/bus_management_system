/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Favoriteroutes;
import com.busplanner.pojo.Users;
import com.busplanner.services.FavoriteRouteService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoriteRouteController {
    
    @Autowired
    private FavoriteRouteService favoriteRouteService;
    
    @GetMapping("/admin/faroutes")
    public String favouriteRouteList(Model model) {
        List<Favoriteroutes> favoriteRoutes = favoriteRouteService.getAllFavoriteRoutes();
        model.addAttribute("favoriteRoutes", favoriteRoutes);
        return "FaRoutes";
    }
}
