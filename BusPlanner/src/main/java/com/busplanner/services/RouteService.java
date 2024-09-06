/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.dto.RouteDto;
import com.busplanner.dto.RouteSuggestion;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Stops;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.simple.parser.ParseException;



public interface RouteService {
    List<RouteDto> getListRoutes(Map<String, String> params);
    void addOrUpdateRoute(Routes route);
    Routes getRouteById(int id);
    void deleteRouteById(int id);
    List<RouteSuggestion> findRoutes(String startPoint, String endPoint, Stops startStop, Stops endStop);
    List<RouteSuggestion> calculateRouteDetails(List<RouteSuggestion> routeSuggestions) throws IOException, ParseException;
}
