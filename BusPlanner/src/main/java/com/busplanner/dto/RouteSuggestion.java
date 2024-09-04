/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.dto;

import com.busplanner.pojo.Buses;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
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
@JsonIgnoreProperties({"walkingDistanceToStartStop", "walkingTimeToStartStop",
    "busDistance", "busTime", "walkingDistanceToEndPoint", "walkingTimeToEndPoint"
})
public class RouteSuggestion {
    // Điểm đi và điểm đến mà người dùng nhập
    private String startPoint;
    private String endPoint;

    // Điểm dừng gần nhất với điểm đi và điểm đến
    private StopDto startStop;

    // Tuyến trực tiếp hoặc tuyến bắt đầu trong trường hợp chuyển tiếp
    private RouteDto startRoute;
    private List<Buses> busesForStartRoute; // Danh sách xe buýt cho tuyến bắt đầu
    
    //Stop sẽ xuống và đợi Route chuyển tiếp (nếu có)
    private StopDto transferStop; // nếu không có Route chuyển tiếp, nó sẽ là null
    
    // Tuyến chuyển tiếp (nếu có). Như thế sẽ là chỉ tìm tối đa 2 route (chuyển tiếp chỉ 1 lần)
    private RouteDto transferRoute;
    private List<Buses> busesForTransferRoute; // Danh sách xe buýt cho tuyến chuyển tiếp, nếu có
    
    //Stop cuối cùng mà người dùng sẽ xuống
    private StopDto endStop; 

    // Khoảng cách và thời gian đi bộ từ điểm đi tới điểm dừng gần nhất
    private double walkingDistanceToStartStop;
    private double walkingTimeToStartStop;

    // Khoảng cách và thời gian đi xe buýt giữa các điểm dừng
    private double busDistance;
    private double busTime;

    // Khoảng cách và thời gian đi bộ từ điểm dừng cuối tới điểm đến
    private double walkingDistanceToEndPoint;
    private double walkingTimeToEndPoint;
    
    private double distanceTotal;
    private double durationTotal;

    // Constructor
    public RouteSuggestion(String startPoint, String endPoint, 
            RouteDto startRoute, StopDto startStop, StopDto transferStop,
            StopDto endStop, RouteDto transferRoute) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startRoute = startRoute;
        this.startStop = startStop;
        this.transferStop = transferStop;
        this.endStop = endStop;
        this.transferRoute = transferRoute;
    }
}
