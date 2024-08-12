/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Stops;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface StopRepository {
    List<Stops> retrieveStop();
    void addOrUpdateStop(Stops stop);
    Stops getStopById(int id);
    void deleteStop(int id);
}
