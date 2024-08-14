/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Routes;
import com.busplanner.services.RouteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
public class RouteServiceImplement implements RouteService{
    
    @Autowired
    private RouteService routeService;

    @Override
    @Transactional
    public List<Routes> getListRoutes() {
        return routeService.getListRoutes();
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
