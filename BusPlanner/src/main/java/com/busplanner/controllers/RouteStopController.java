/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Routestops;
import com.busplanner.services.RouteStopService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Admin
 */
@Controller
public class RouteStopController {

    @Autowired
    private RouteStopService routeStopService;

    @PostMapping("/admin/routestop/delete/{routeStopId}")
    public String deleteRouteStop(Model model, @PathVariable("routeStopId") int routeStopId) {
        routeStopService.deleteRouteStop(routeStopId);
        return "redirect:/admin/routes";
    }

    @PostMapping("/admin/routestop/update")
    public String updateRouteStopsOrder(@RequestParam Map<String, String> parameters,
                                        RedirectAttributes redirectAttributes) {
        try {
            // Parse the parameters to get route stops and their new orders
            List<Routestops> routeStops = new ArrayList<>();
            int index = 0;
            // với mỗi Stop của route, tạo 1 đối tượng Routestops(routeStopId - để biết update Routestops nào, stopOrder)
            while (parameters.containsKey("routeStops[" + index + "].routeStopId")) {
                Integer routeStopId = Integer.parseInt(parameters.get("routeStops[" + index + "].routeStopId"));
                Integer stopOrder = Integer.parseInt(parameters.get("routeStops[" + index + "].stopOrder"));
                routeStops.add(new Routestops(routeStopId, stopOrder));
                index++;
            }

            // Call the service method to update stop orders
            routeStopService.updateRouteStopsOrder(routeStops);
            return "redirect:/admin/routes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật thứ tự điểm dừng: " + e.getMessage());
        }
        return "redirect:/admin/routes";
    }
}
