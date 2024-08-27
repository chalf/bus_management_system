/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Stops;
import com.busplanner.services.StopService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StopController {
    
    @Autowired
    private StopService stopService;
    
    @GetMapping("/admin/stops")
    public String stopList(Model model,
                           @RequestParam(value = "q", required = false) String query,
                           @RequestParam(value = "name", required = false) String stopName) {
        // Create a map to hold the parameters
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            params.put("q", query);
        }
        if (stopName != null && !stopName.isEmpty()) {
            params.put("name", stopName);
        }

        // Pass the parameters to the service
        model.addAttribute("stops", stopService.retrieveStop(params));
        model.addAttribute("stop", new Stops());

        return "stopPage"; // The name of your JSP file
    }

    @PostMapping("/admin/stops")
    public String addOrUpdateStop(Model model, @ModelAttribute(value = "stop") Stops stop,
                                  RedirectAttributes redirectAttributes) {
        try {
            stopService.addorUpdateStop(stop);
            return "successAdding";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "stopPage"; // The name of your JSP file
        }
    }
    
    @PostMapping("/admin/stops/delete/{stopId}")
    public String deleteStop(@PathVariable("stopId") int stopId, Model model) {
        try {
            stopService.deleteStop(stopId);
            return "redirect:/admin/stops";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "stopPage";
        }
    }
    
}
