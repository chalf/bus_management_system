/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.pojo.Stops;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface StopService {
    List<Stops> retrieveStop();
    void addorUpdateStop(Stops stop);
    public Stops getStopById(int id);
    void deleteStop(int id);
}
