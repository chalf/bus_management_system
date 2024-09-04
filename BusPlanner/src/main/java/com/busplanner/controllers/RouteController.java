/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Routestops;
import com.busplanner.pojo.Stops;
import com.busplanner.services.RouteService;
import com.busplanner.services.RouteStopService;
import com.busplanner.services.StopService;
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

    @Autowired
    private RouteStopService routestopService;

    @Autowired
    private StopService stopService;

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
    public String addRoute(Model model, @ModelAttribute(value = "route") Routes route) {
        try {
            routeService.addOrUpdateRoute(route);
            return "successAdding";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "routePage";
    }

    @GetMapping("/admin/route/{id}")
    public String getRouteDetails(@PathVariable("id") int id, Model model) {
        Routes route = routeService.getRouteById(id);
        List<Routestops> routeStops = routestopService.getRouteStopsByRouteId(id);
        List<Stops> stops = stopService.retrieveStop(null);

        int nextOrder = routeStops.isEmpty() ? 1 : routeStops.get(routeStops.size() - 1).getStopOrder() + 1;

        model.addAttribute("route", route);
        model.addAttribute("stops", stops);
        model.addAttribute("routeStops", routeStops);
        model.addAttribute("nextOrder", nextOrder);
        model.addAttribute("routeStop", new Routestops()); // Add this line

        return "routeDetailPage";
    }

    @PostMapping("/admin/routes/{id}/")
    public String addRouteStop(@PathVariable("id") int routeId,
            @RequestParam("stopId") int stopId,
            @RequestParam("stopOrder") int order,
            @RequestParam("direction") String direction) {
        Routes route = routeService.getRouteById(routeId);
        Stops stop = stopService.getStopById(stopId);

        Routestops routeStop = new Routestops();
        routeStop.setRouteId(route);
        routeStop.setStopId(stop);
        routeStop.setStopOrder(order);
        routeStop.setDirection(direction);

        routestopService.addOrUpdateRouteStop(routeStop);
        return "redirect:/admin/route/" + routeId;
    }

    @PostMapping("/admin/routes/delete/{routeId}")
    public String deleteRoute(@PathVariable("routeId") int routeId, Model model) {
        try {
            routeService.deleteRouteById(routeId);
            return "redirect:/admin/routes";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "routePage";
        }
    }

    @PostMapping("/admin/routes/update/{id}")
    public String updateRoute(@PathVariable("id") int routeId, @ModelAttribute("route") Routes route, Model model) {
        try {
            // Set the route ID to ensure it's updating the correct route
            route.setRouteId(routeId);
            routeService.addOrUpdateRoute(route); // Use existing function to save or update the route
            return "redirect:/admin/route/" + routeId; // Redirect back to the route detail page
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "routeDetailPage"; // Return to the route detail page if there's an error
        }
    }
}
