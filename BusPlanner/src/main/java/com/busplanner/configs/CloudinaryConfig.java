/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.configs;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Admin
 */
@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(com.cloudinary.utils.ObjectUtils.asMap(
                "cloud_name", "dsfdkyanf",
                "api_key", "474893993165787",
                "api_secret", "vjNSGVLgza4SMM5pvfRtnx8SFBw",
                "secure", true));
        return cloudinary;
    }

}
