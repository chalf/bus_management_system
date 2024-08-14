/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.services;

import com.busplanner.pojo.Trafficreports;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface TrafficReportService {
    List<Trafficreports> getAllTrafficReports();                // Retrieve all traffic reports
    void addOrUpdateTrafficReport(Trafficreports report);       // Add or update a traffic report
    Trafficreports getTrafficReportById(int reportId);          // Retrieve a traffic report by its ID
    void deleteTrafficReportById(int reportId);                 // Delete a traffic report by its ID
    List<Trafficreports> getTrafficReportsByUserId(int userId); // Retrieve traffic reports by user ID
}
