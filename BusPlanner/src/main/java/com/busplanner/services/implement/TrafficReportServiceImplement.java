/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Trafficreports;
import com.busplanner.repositories.TrafficReportRepository;
import com.busplanner.services.TrafficReportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ASUS
 */
public class TrafficReportServiceImplement implements TrafficReportService{
    
    @Autowired
    private TrafficReportRepository trafficReportRepository;

    @Override
    public List<Trafficreports> getAllTrafficReports() {
        return trafficReportRepository.getAllTrafficReports();
    }

    @Override
    public void addOrUpdateTrafficReport(Trafficreports report) {
        trafficReportRepository.addOrUpdateTrafficReport(report);
    }

    @Override
    public Trafficreports getTrafficReportById(int reportId) {
        return trafficReportRepository.getTrafficReportById(reportId);
    }

    @Override
    public void deleteTrafficReportById(int reportId) {
        trafficReportRepository.deleteTrafficReportById(reportId);
    }

    @Override
    public List<Trafficreports> getTrafficReportsByUserId(int userId) {
        return trafficReportRepository.getTrafficReportsByUserId(userId);
    }
    
}
