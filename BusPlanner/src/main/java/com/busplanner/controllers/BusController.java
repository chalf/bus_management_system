/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Buses;
import com.busplanner.services.BusService;
import com.busplanner.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Admin
 */
@Controller
public class BusController {
    @Autowired
    private BusService busService;
    @Autowired
    private RouteService routeService;
    
    @GetMapping("/admin/buses")
    public String busList(Model model){
        model.addAttribute("buses", busService.getListBus());
        model.addAttribute("bus", new Buses());
        model.addAttribute("routes", routeService.getListRoutes(null));
        return "busPage";
    }
    
    @PostMapping("/admin/buses")
    public String addBus(Model model, @ModelAttribute(value = "bus") Buses bus){
        try {
            busService.addOrUpdateBus(bus);
            return "successAdding";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "busPage";
    }
}
