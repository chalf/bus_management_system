/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Stops;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public interface StopRepository {
    List<Stops> retrieveStop(Map<String, String> params);
    void addOrUpdateStop(Stops stop);
    Stops getStopById(int id);
    void deleteStop(int id);
    List<Stops> getListStop();
}
