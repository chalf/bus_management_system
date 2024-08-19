/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Routes;
import com.busplanner.services.RouteService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Service
public class RouteServiceImplement implements RouteService{
    
    @Autowired
    private RouteService routeService;

    @Override
    @Transactional
    public List<Routes> getListRoutes(Map<String, String> params) {
        return routeService.getListRoutes(params);
    }

    @Override
    @Transactional
    public void addOrUpdateRoute(Routes route) {
        routeService.addOrUpdateRoute(route);
    }

    @Override
    @Transactional
    public Routes getRouteById(int id) {
        return routeService.getRouteById(id);
    }

    @Override
    public void deleteRouteById(int id) {
        routeService.deleteRouteById(id);
    }
    
}
