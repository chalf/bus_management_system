/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.dto.RouteSuggestion;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Routestops;
import com.busplanner.pojo.Stops;
import com.busplanner.repositories.RouteRepository;
import com.busplanner.repositories.RouteStopRepository;
import com.busplanner.repositories.StopRepository;
import com.busplanner.services.RouteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RouteServiceImplement implements RouteService {

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StopRepository stopRepository;
    @Autowired
    private RouteStopRepository routeStopRepository;

    @Override
    @Transactional
    public List<Routes> getListRoutes(Map<String, String> params) {
        return routeRepository.getListRoutes(params);
    }

    @Override
    @Transactional
    public void addOrUpdateRoute(Routes route) {
        routeRepository.addOrUpdateRoute(route);
    }

    @Override
    @Transactional
    public Routes getRouteById(int id) {
        return routeRepository.getRouteById(id);
    }

    @Override
    public void deleteRouteById(int id) {
        routeRepository.deleteRouteById(id);
    }

    @Override
    public List<RouteSuggestion> findRoutes(Stops nearestStartStop, Stops nearestEndStop) {
        List<RouteSuggestion> routeSuggestions = new ArrayList<>();
        // Kiểm tra nếu điểm dừng tồn tại
        if (nearestStartStop == null || nearestEndStop == null) {
            throw new IllegalArgumentException("Start or end stop does not exist");
        }

        //1. Tìm tất cả các tuyến đi qua điểm dừng gần nhất với `điểm đi`
        List<Routes> startRoutes = routeRepository.findByStopId(nearestStartStop.getStopId());
        // 2. Tìm tất cả các tuyến đi qua điểm dừng gần nhất của `điểm đến`
        List<Routes> endRoutes = routeRepository.findByStopId(nearestEndStop.getStopId());

        // 3. Tìm tuyến trực tiếp (nếu có)
        for (Routes startRoute : startRoutes) {
            if (endRoutes.contains(startRoute)) {
                routeSuggestions.add(new RouteSuggestion(startRoute, nearestStartStop, nearestEndStop, null));
            }
        }

        // 4. Tìm tuyến chuyển tiếp (nếu không có tuyến trực tiếp)
        if (routeSuggestions.isEmpty()) {
            // Tìm các điểm dừng trung gian có thể đổi tuyến
            for (Routes startRoute : startRoutes) {
                List<Stops> stopsOnStartRoute = new ArrayList<>();
                List<Routestops> routeStopList = routeStopRepository.getRouteStopsByRouteId(startRoute.getRouteId());
                for (Routestops rs : routeStopList) {
                    stopsOnStartRoute.add(rs.getStopId());
                }

                for (Stops stop : stopsOnStartRoute) {
                    // Kiểm tra xem có tuyến nào từ điểm dừng trung gian này đến điểm đến không
                    List<Routes> transferRoutes = routeRepository.findByStopId(stop.getStopId());
                    for (Routes transferRoute : transferRoutes) {
                        if (endRoutes.contains(transferRoute)) {
                            routeSuggestions.add(new RouteSuggestion(startRoute, nearestStartStop, stop, transferRoute));
                        }
                    }
                }
            }
        }

        return routeSuggestions;
    }

    @Override
    public List<RouteSuggestion> calculateRouteDetails(List<RouteSuggestion> routeSuggestions) {
//        for (RouteSuggestion suggestion : routeSuggestions) {
//            // 1. Tính khoảng cách và thời gian đi bộ từ điểm đi tới điểm dừng gần nhất
//            double walkingDistanceToStartStop = calculateWalkingDistance(suggestion.getStartPoint(), suggestion.getStartStop());
//            double walkingTimeToStartStop = calculateWalkingTime(suggestion.getStartPoint(), suggestion.getStartStop());
//
//            suggestion.setWalkingDistanceToStartStop(walkingDistanceToStartStop);
//            suggestion.setWalkingTimeToStartStop(walkingTimeToStartStop);
//
//            // 2. Tính khoảng cách và thời gian đi xe buýt giữa các điểm dừng
//            double busDistance = calculateBusDistance(suggestion);
//            double busTime = calculateBusTime(suggestion);
//
//            suggestion.setBusDistance(busDistance);
//            suggestion.setBusTime(busTime);
//
//            // 3. Tính khoảng cách và thời gian đi bộ từ điểm dừng cuối tới điểm đến
//            double walkingDistanceToEndPoint = calculateWalkingDistance(suggestion.getEndStop(), suggestion.getEndPoint());
//            double walkingTimeToEndPoint = calculateWalkingTime(suggestion.getEndStop(), suggestion.getEndPoint());
//
//            suggestion.setWalkingDistanceToEndPoint(walkingDistanceToEndPoint);
//            suggestion.setWalkingTimeToEndPoint(walkingTimeToEndPoint);
//        }
        return routeSuggestions;
    }

}
