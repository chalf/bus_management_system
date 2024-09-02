/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.component.DistanceMatrixService;
import com.busplanner.dto.RouteDto;
import com.busplanner.dto.RouteSuggestion;
import com.busplanner.dto.StopDto;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Routestops;
import com.busplanner.pojo.Stops;
import com.busplanner.repositories.RouteRepository;
import com.busplanner.repositories.RouteStopRepository;
import com.busplanner.repositories.StopRepository;
import com.busplanner.services.RouteService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.parser.ParseException;
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
    public List<RouteSuggestion> findRoutes(String startPoint, String endPoint,
            Stops nearestStartStop, Stops nearestEndStop) {
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
                StopDto nearestStartStopDto = new StopDto(nearestStartStop.getStopId(), nearestStartStop.getStopName(), nearestStartStop.getLatitude(), nearestStartStop.getLongitude(), nearestStartStop.getAddress());
                StopDto nearestEndStopDto = new StopDto(nearestEndStop.getStopId(), nearestEndStop.getStopName(), nearestEndStop.getLatitude(), nearestEndStop.getLongitude(), nearestEndStop.getAddress());
                RouteDto startRouteDto = new RouteDto(startRoute.getRouteId(), startRoute.getRouteName(), startRoute.getStartPoint(), startRoute.getEndPoint(), startRoute.getDirection());
                
                routeSuggestions.add(new RouteSuggestion(startPoint, endPoint, startRouteDto,
                        nearestStartStopDto, nearestEndStopDto, null));
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
                            StopDto nearestStartStopDto = new StopDto(nearestStartStop.getStopId(), nearestStartStop.getStopName(), nearestStartStop.getLatitude(), nearestStartStop.getLongitude(), nearestStartStop.getAddress());
                            
                            StopDto stopDto = new StopDto(stop.getStopId(), stop.getStopName(), stop.getLatitude(), stop.getLongitude(), stop.getAddress());
                            RouteDto startRouteDto = new RouteDto(startRoute.getRouteId(), startRoute.getRouteName(), startRoute.getStartPoint(), startRoute.getEndPoint(), startRoute.getDirection());
                            RouteDto transferRouteDto = new RouteDto(transferRoute.getRouteId(), transferRoute.getRouteName(), transferRoute.getStartPoint(), transferRoute.getEndPoint(), transferRoute.getDirection());
                            routeSuggestions.add(new RouteSuggestion(startPoint, endPoint, startRouteDto, nearestStartStopDto, stopDto, transferRouteDto));
                        }
                    }
                }
            }
        }

        return routeSuggestions;
    }

    @Autowired
    private DistanceMatrixService distanceCalculator;

    @Override
    public List<RouteSuggestion> calculateRouteDetails(List<RouteSuggestion> routeSuggestions) throws IOException, ParseException {
        for (RouteSuggestion suggestion : routeSuggestions) {
            // 1. Tính khoảng cách và thời gian đi bộ từ điểm đi tới điểm dừng gần nhất
            DistanceMatrixService.DistanceTimeResult walkingToStartStop
                    = distanceCalculator.calculateWalkingDetails(suggestion.getStartPoint(), suggestion.getStartStop().getLatitude() + ", " + suggestion.getStartStop().getLongitude());

            suggestion.setWalkingDistanceToStartStop(walkingToStartStop.distance);
            suggestion.setWalkingTimeToStartStop(walkingToStartStop.duration);

            // 2. Tính khoảng cách và thời gian đi xe buýt giữa các điểm dừng
            DistanceMatrixService.DistanceTimeResult busDetails
                    = distanceCalculator.calculateTransitDetails(suggestion.getStartStop().getLatitude() + ", " + suggestion.getStartStop().getLongitude(), suggestion.getEndStop().getLatitude() + ", " + suggestion.getEndStop().getLongitude());

            suggestion.setBusDistance(busDetails.distance);
            suggestion.setBusTime(busDetails.duration);

            // 3. Tính khoảng cách và thời gian đi bộ từ điểm dừng cuối tới điểm đến
            DistanceMatrixService.DistanceTimeResult walkingToEndPoint
                    = distanceCalculator.calculateWalkingDetails(suggestion.getEndStop().getLatitude() + ", " + suggestion.getEndStop().getLongitude(), suggestion.getEndPoint());

            suggestion.setWalkingDistanceToEndPoint(walkingToEndPoint.distance);
            suggestion.setWalkingTimeToEndPoint(walkingToEndPoint.duration);

            suggestion.setDistanceTotal(suggestion.getWalkingDistanceToStartStop()
                    + suggestion.getBusDistance()
                    + suggestion.getWalkingDistanceToEndPoint()
            );
            suggestion.setDurationTotal(suggestion.getWalkingTimeToStartStop()
                    + suggestion.getBusTime()
                    + suggestion.getWalkingTimeToEndPoint()
            );
        }
        return routeSuggestions;
    }

}
