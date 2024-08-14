/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.pojo.Routes;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface RouteService {
    List<Routes> getListRoutes();
    void addOrUpdateRoute(Routes route);
    Routes getRouteById(int id);
    void deleteRouteById(int id);
}
