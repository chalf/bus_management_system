/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Buses;
import com.busplanner.services.BusService;
import com.busplanner.services.RouteService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String busList(Model model) {
        model.addAttribute("buses", busService.getListBus());
        model.addAttribute("bus", new Buses());
        model.addAttribute("routes", routeService.getListRoutes(null));
        return "busPage";
    }

    @PostMapping("/admin/buses")
    public String addBus(Model model, @Valid @ModelAttribute(value = "bus") Buses bus, BindingResult err) {
        if (!err.hasErrors()) {
            try {
                busService.addOrUpdateBus(bus);
                return "redirect:/admin/buses";
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
            }
        }
        return "busPage";
    }

    @PostMapping("/admin/buses/delete/{busId}")
    public String deleteBus(@PathVariable("busId") int busId, Model model) {
        try {
            busService.deleteBusById(busId);
            return "redirect:/admin/buses";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "busPage";
        }
    }

    @GetMapping("/admin/buses/edit/{busId}")
    public String editBus(@PathVariable("busId") int busId, Model model) {
        Buses bus = busService.getBusById(busId);
        if (bus == null) {
            model.addAttribute("errorMessage", "Bus not found");
        } else {
            model.addAttribute("bus", bus);
        }
        model.addAttribute("buses", busService.getListBus());
        model.addAttribute("routes", routeService.getListRoutes(null));
        return "busPage";
    }

    @PostMapping("/admin/buses/update")
    public String updateBus(@ModelAttribute("bus") Buses bus, Model model) {
        try {
            busService.addOrUpdateBus(bus);
            return "redirect:/admin/buses";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("buses", busService.getListBus());
            model.addAttribute("routes", routeService.getListRoutes(null));
            return "busPage";
        }
    }
}
