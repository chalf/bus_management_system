/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.component.GeocodingService;
import com.busplanner.dto.RouteSuggestion;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Stops;
import com.busplanner.services.RouteService;
import com.busplanner.services.StopService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ParseException;
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
    private GeocodingService geocoding;

    @Autowired
    private StopService stopService;

    @Autowired
    private RouteService routeService;
    
    
    @GetMapping("/find")
    public ResponseEntity<?> findRoute(@RequestParam(value = "origin") String origin,
            @RequestParam(value = "destination") String dest) throws IOException, ParseException {
        // Lấy tọa độ của điểm đi và điểm đến mà người dùng nhập
        Map<String, String> startCoordinate = geocoding.geocoding(origin);
        Map<String, String> endCoordinate = geocoding.geocoding(dest);
        
        // validate địa chỉ mà người dùng nhập, nếu GoogleMap không tìm thấy tọa độ => địa chỉ không hợp lệ
        if(startCoordinate.isEmpty()){
            return new ResponseEntity<>("Không tìm thấy địa chỉ: `"+ origin + "`", HttpStatus.BAD_REQUEST);
        }
        if(endCoordinate.isEmpty()){
            return new ResponseEntity<>("Không tìm thấy địa chỉ: `"+ dest + "`", HttpStatus.BAD_REQUEST);
        }
        
        // Tìm điểm dừng gần nhất cho điểm đi và điểm đến
        Stops nearestStartStop = stopService.findNearestStop(Double.parseDouble(startCoordinate.get("latitude")), Double.parseDouble(startCoordinate.get("longitude")));
        Stops nearestEndStop = stopService.findNearestStop(Double.parseDouble(endCoordinate.get("latitude")), Double.parseDouble(endCoordinate.get("longitude")));
       
        // Tìm tuyến đường từ điểm đi tới điểm đến
        List<RouteSuggestion> routeSuggestions = routeService.findRoutes(
                origin,
                dest,
                nearestStartStop, nearestEndStop);
        
        
        
        // Gọi Google Maps API để tính toán khoảng cách và thời gian
        routeSuggestions = routeService.calculateRouteDetails(routeSuggestions);

        return ResponseEntity.ok(routeSuggestions);
    }

}
