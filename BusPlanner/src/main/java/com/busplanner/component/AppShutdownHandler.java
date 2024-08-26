/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
//Đảm bảo gọi context.shutdown() khi ứng dụng dừng để giải phóng tài nguyên.
@Component
public class AppShutdownHandler {
    @Autowired
    private GeocodingService geocodingService;

    @EventListener
    public void onApplicationEvent(ContextClosedEvent event) {
        geocodingService.shutdown();
    }
}
