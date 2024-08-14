/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Schedules;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public interface ScheduleRepository {
    List<Schedules> getAllSchedules(Map<String, String> params);
    Schedules getScheduleById(int scheduleId);
    Schedules addOrUpdateSchedule(Schedules schedule);
    void deleteSchedule(int scheduleId);
    List<Schedules> getSchedulesByBusId(int busId);
    List<Schedules> getSchedulesByRouteId(int routeId);
}
