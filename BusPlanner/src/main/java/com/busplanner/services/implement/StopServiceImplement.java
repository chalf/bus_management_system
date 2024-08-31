/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.busplanner.resources.Utils;
import com.busplanner.component.GeocodingService;
import com.busplanner.pojo.Stops;
import com.busplanner.repositories.StopRepository;
import com.busplanner.services.StopService;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Service
public class StopServiceImplement implements StopService {

    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private GeocodingService geo;

    @Override
    @Transactional
    public List<Stops> retrieveStop(Map<String, String> params) {
        return stopRepository.retrieveStop(params);
    }

    @Override
    @Transactional
    public void addorUpdateStop(Stops stop){
        try {
            Map<String, String> coordinate = geo.geocoding(stop.getAddress());
            stop.setLatitude(new BigDecimal(coordinate.get("latitude")));
            stop.setLongitude(new BigDecimal(coordinate.get("longitude")));
            stopRepository.addOrUpdateStop(stop);
        } catch (IOException ex) {
            Logger.getLogger(StopServiceImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Transactional
    public Stops getStopById(int id) {
        return stopRepository.getStopById(id);
    }

    @Override
    @Transactional
    public void deleteStop(int id) {
        stopRepository.deleteStop(id);
    }

    @Override
    public Stops findNearestStop(double userLatitude, double userLongitude) {
        List<Stops> stops = stopRepository.getListStop();
        Stops nearestStop = null;
        double minDistance = Double.MAX_VALUE;

        for (Stops stop : stops) {
            // Chuyển đổi BigDecimal sang double
            double stopLat = stop.getLatitude().doubleValue();
            double stopLon = stop.getLongitude().doubleValue();
            double distance = Utils.calculateDistance(userLatitude, userLongitude, stopLat, stopLon);
            if (distance < minDistance) {
                minDistance = distance;
                nearestStop = stop;
            }
        }

        return nearestStop;
    }

}
