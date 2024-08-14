/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Stops;
import com.busplanner.repositories.StopRepository;
import com.busplanner.services.StopService;
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
public class StopServiceImplement  implements StopService{

    @Autowired
    private StopRepository stopRepository;
    
    @Override
    @Transactional
    public List<Stops> retrieveStop(Map<String, String> params) {
        return stopRepository.retrieveStop(params);
    }

    @Override
    @Transactional
    public void addorUpdateStop(Stops stop) {
        stopRepository.addOrUpdateStop(stop);
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
    
}
