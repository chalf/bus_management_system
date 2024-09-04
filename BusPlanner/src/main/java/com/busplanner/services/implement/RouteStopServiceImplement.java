/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Routestops;
import com.busplanner.repositories.RouteStopRepository;
import com.busplanner.services.RouteStopService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Service
public class RouteStopServiceImplement implements RouteStopService {

    @Autowired
    private RouteStopRepository routeStopRepository;

    @Override
    public List<Routestops> getAllRouteStops() {
        return routeStopRepository.getAllRouteStops();
    }

    @Override
    public Routestops getRouteStopById(int routeStopId) {
        return routeStopRepository.getRouteStopById(routeStopId);
    }

    @Override
    public Routestops addOrUpdateRouteStop(Routestops routeStop) {
        return routeStopRepository.addOrUpdateRouteStop(routeStop);
    }

    @Override
    public void deleteRouteStop(int routeStopId) {
        routeStopRepository.deleteRouteStop(routeStopId);
    }

    @Override
    public List<Routestops> getRouteStopsByRouteId(int routeId) {
        return routeStopRepository.getRouteStopsByRouteId(routeId);
    }

    @Override
    public List<Routestops> getRouteStopsByStopId(int stopId) {
        return routeStopRepository.getRouteStopsByStopId(stopId);
    }

    @Override
    @Transactional
    public void updateRouteStopsOrder(Integer routeId, List<Routestops> routeStops) {
        for (Routestops routeStop : routeStops) {
            routeStopRepository.updateStopOrder(routeStop.getRouteStopId(), routeStop.getStopOrder());
        }
    }

}
