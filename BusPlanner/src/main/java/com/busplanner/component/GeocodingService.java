/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.component;

import com.busplanner.dto.Coordinates;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
@PropertySource("classpath:config.properties")
public class GeocodingService {

    private final GeoApiContext context;
    
    //build context với api key
    public GeocodingService(@Value("${google.api.key}") String apiKey) {
        this.context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public Coordinates getCoordinates(String address) throws Exception {
        // geocode() ánh xạ địa chỉ thành kinh độ, vĩ độ
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        if (results != null && results.length > 0) {
            double latitude = results[0].geometry.location.lat;
            double longitude = results[0].geometry.location.lng;
            return new Coordinates(latitude, longitude);
        } else {
            throw new Exception("Không thể tìm thấy tọa độ cho địa chỉ: " + address);
        }
    }

    public void shutdown() {
        context.shutdown();
    }
}
