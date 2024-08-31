/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.component.DistanceMatrixService;
import com.busplanner.dto.RouteSuggestion;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Stops;
import com.busplanner.services.RouteService;
import com.busplanner.services.StopService;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/api/route")
public class ApiRouteController {

    @Autowired
    private GeoApiContext geoApiContext;

    @Autowired
    private StopService stopService;

    @Autowired
    private RouteService routeService;

    @GetMapping("/find")
    public ResponseEntity<?> findRoute(
            @RequestParam double startLat, @RequestParam double startLng,
            @RequestParam double endLat, @RequestParam double endLng) {

        // Tìm điểm dừng gần nhất cho điểm đi và điểm đến
        Stops nearestStartStop = stopService.findNearestStop(startLat, startLng);
        Stops nearestEndStop = stopService.findNearestStop(endLat, endLng);

        // Tìm tuyến đường từ điểm đi tới điểm đến
        List<RouteSuggestion> routeSuggestions = routeService.findRoutes(nearestStartStop, nearestEndStop);

        // Gọi Google Maps API để tính toán khoảng cách và thời gian
        routeSuggestions = routeService.calculateRouteDetails(routeSuggestions);

        return ResponseEntity.ok(routeSuggestions);
    }
    
    
    
    
    

}
