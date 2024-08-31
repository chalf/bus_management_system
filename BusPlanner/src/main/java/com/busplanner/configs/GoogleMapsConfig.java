/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.configs;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Admin
 */
@Configuration
@PropertySource("classpath:config.properties")
public class GoogleMapsConfig {
    @Autowired
    private org.springframework.core.env.Environment env;
    public static String API_KEY;
    
    //build context từ apiKey
    @Bean
    public GeoApiContext geoApiContext() {
        setApiKey();
        return new GeoApiContext.Builder()
            .apiKey(API_KEY)
            .build();
    }
    
    
    
    public void setApiKey(){
        API_KEY = env.getProperty("google.api.key");
    }
}
