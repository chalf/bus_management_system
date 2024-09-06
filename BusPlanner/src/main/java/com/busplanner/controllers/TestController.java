/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.component.DistanceMatrixService;
import com.busplanner.component.GeocodingService;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Admin
 */
@Controller
public class TestController {

    @Autowired
    private GeocodingService geo;

//    @RequestMapping("/test/geocoding")
    public String geocode(Model model, @RequestParam(value = "address") String address) throws IOException {
        Map<String, String> coor = geo.geocoding(address);
        if (coor.isEmpty()) {
            model.addAttribute("geo", "Không tìm thấy địa chỉ: `"+ address + "`"); 
        }else
            model.addAttribute("geo", geo.geocoding(address));
        return "test";
    }
    // TEST distance matrix api
    @Autowired
    private DistanceMatrixService distance;

//    @GetMapping("/test/distance")
    public ResponseEntity<?> distance(@RequestParam("picking_up") String source, @RequestParam("dropping_off") String destination) {
        String response = "";
        try {
            //method of DistanceTime Class
            response = distance.sendRequest(source, destination, "walking");

        } catch (Exception e) {
            System.out.println("Exception Occurred");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    
//    @RequestMapping("/login/google")
//    public Map<String, Object> loginGoogle(OAuth2 googleUSer){
//        
//    }
}
