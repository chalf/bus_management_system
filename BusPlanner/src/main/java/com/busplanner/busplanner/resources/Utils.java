/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.busplanner.resources;

/**
 *
 * @author Admin
 */
public class Utils {

    /* Haversine formula (công thức Haversine) được sử dụng để tính khoảng cách 
    giữa hai điểm trên bề mặt của một hình cầu,
    dựa trên vĩ độ và kinh độ của chúng */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int earthRadius = 6371; // Radius of the Earth in kilometers
        //Tính toán sự chênh lệch vĩ độ và kinh độ
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        // áp dụng công thức Haversine
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // tính toán khoảng cách
        return earthRadius * c; // convert to kilometers
    }


}
