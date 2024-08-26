/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.pojo.Stops;
import com.busplanner.services.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/stops")
public class ApiStopController {
    @Autowired
    private StopService stopService;
    
    // truyền vô vĩ độ và kinh độ của điểm đi, trả ra Stops gần nhất
    @GetMapping(path="/nearest-stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stops> getNearestStop(@RequestParam(value = "latitude") double lat, 
                                @RequestParam(value = "longitude") double lon) {
        Stops nearestStop = stopService.findNearestStop(lat, lon);
        return new ResponseEntity<>(nearestStop, HttpStatus.OK);
    }
}
