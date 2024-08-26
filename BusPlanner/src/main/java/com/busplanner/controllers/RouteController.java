/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Routes;
import com.busplanner.services.RouteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/admin/routes")
    public String busList(Model model,
            @RequestParam(value = "q", required = false) String query,
            @RequestParam(value = "start", required = false) String startPoint,
            @RequestParam(value = "end", required = false) String endPoint,
            @RequestParam(value = "page", required = false) String page) {
        // Create a map to hold the parameters
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            params.put("q", query);
        }
        if (startPoint != null && !startPoint.isEmpty()) {
            params.put("start", startPoint);
        }
        if (endPoint != null && !endPoint.isEmpty()) {
            params.put("end", endPoint);
        }
        if (page != null && !page.isEmpty()) {
            params.put("page", page);
        }

        // Pass the parameters to the service
        model.addAttribute("routes", routeService.getListRoutes(params));
        model.addAttribute("route", new Routes());

        return "routePage";
    }
    @PostMapping("/admin/routes")
    public String addRoute(Model model, @ModelAttribute(value = "route") Routes route){
        try {
            routeService.addOrUpdateRoute(route);
            return "successAdding";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "routePage";
    }
}
