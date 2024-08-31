/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.dto;

import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Stops;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@NoArgsConstructor
public class RouteSuggestion {
    // Điểm đi và điểm đến mà người dùng nhập
    private String startPoint;
    private String endPoint;

    // Điểm dừng gần nhất với điểm đi và điểm đến
    private Stops startStop;
    private Stops endStop;

    // Tuyến trực tiếp hoặc tuyến bắt đầu trong trường hợp chuyển tiếp
    private Routes startRoute;
    // Tuyến chuyển tiếp (nếu có)
    private Routes transferRoute;

    // Khoảng cách và thời gian đi bộ từ điểm đi tới điểm dừng gần nhất
    private double walkingDistanceToStartStop;
    private double walkingTimeToStartStop;

    // Khoảng cách và thời gian đi xe buýt giữa các điểm dừng
    private double busDistance;
    private double busTime;

    // Khoảng cách và thời gian đi bộ từ điểm dừng cuối tới điểm đến
    private double walkingDistanceToEndPoint;
    private double walkingTimeToEndPoint;

    // Constructor
    public RouteSuggestion(Routes startRoute, Stops startStop, Stops endStop, Routes transferRoute) {
        this.startRoute = startRoute;
        this.startStop = startStop;
        this.endStop = endStop;
        this.transferRoute = transferRoute;
    }
}
