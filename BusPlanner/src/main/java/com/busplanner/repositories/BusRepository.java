/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Buses;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface BusRepository {
    List<Buses> getListBus();
    void addOrUpdateBus(Buses bus);
    Buses getBusById(int id);
    void deleteBusbyId(int id);
}
