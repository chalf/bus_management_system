/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Routes;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public interface RouteRepository {
    List<Routes> getListRoutes(Map<String, String> params);
    void addOrUpdateRoute(Routes route);
    Routes getRouteById(int id);
    void deleteRouteById(int id);
}
