/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Schedules;
import com.busplanner.repositories.ScheduleRepository;
import com.busplanner.services.ScheduleService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASUS
 */
@Service
public class ScheduleServiceImplement implements ScheduleService{
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedules> getAllSchedules(Map<String, String> params) {
        return scheduleRepository.getAllSchedules(params);
    }

    @Override
    public Schedules getScheduleById(int scheduleId) {
        return scheduleRepository.getScheduleById(scheduleId);
    }

    @Override
    public Schedules addOrUpdateSchedule(Schedules schedule) {
        return scheduleRepository.addOrUpdateSchedule(schedule);
    }

    @Override
    public void deleteSchedule(int scheduleId) {
        scheduleRepository.deleteSchedule(scheduleId);
    }

    @Override
    public List<Schedules> getSchedulesByBusId(int busId) {
        return scheduleRepository.getSchedulesByBusId(busId);
    }

    @Override
    public List<Schedules> getSchedulesByRouteId(int routeId) {
        return scheduleRepository.getSchedulesByRouteId(routeId);
    }
    
}
