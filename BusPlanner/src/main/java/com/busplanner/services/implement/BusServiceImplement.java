/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Buses;
import com.busplanner.repositories.BusRepository;
import com.busplanner.services.BusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Service
public class BusServiceImplement implements BusService{
    
    @Autowired
    private BusRepository busRepository;

    @Override
    @Transactional
    public List<Buses> getListBus() {
        return busRepository.getListBus();
    }

    @Override
    @Transactional
    public void addOrUpdateBus(Buses bus) {
        busRepository.addOrUpdateBus(bus);
    }

    @Override
    @Transactional
    public Buses getBusById(int busId) {
        return busRepository.getBusById(busId);
    }

    @Override
    @Transactional
    public void deleteBusById(int busId) {
        busRepository.deleteBusById(busId);
    }

    @Override
    @Transactional
    public List<Buses> getBusesByRouteId(int routeId) {
        return busRepository.getBusesByRouteId(routeId);
    }
    
}
