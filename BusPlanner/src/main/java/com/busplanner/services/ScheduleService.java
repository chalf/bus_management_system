/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.pojo.Schedules;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface ScheduleService {
    List<Schedules> getAllSchedules();
    Schedules getScheduleById(int scheduleId);
    Schedules addOrUpdateSchedule(Schedules schedule);
    void deleteSchedule(int scheduleId);
    List<Schedules> getSchedulesByBusId(int busId);
    List<Schedules> getSchedulesByRouteId(int routeId);
}
