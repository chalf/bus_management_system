/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.pojo.Routestops;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface RouteStopService {
    List<Routestops> getAllRouteStops();
    Routestops getRouteStopById(int routeStopId);
    Routestops addOrUpdateRouteStop(Routestops routeStop);
    void deleteRouteStop(int routeStopId);
    List<Routestops> getRouteStopsByRouteId(int routeId);
    List<Routestops> getRouteStopsByStopId(int stopId);
    void updateRouteStopsOrder(List<Routestops> routeStops);
}
