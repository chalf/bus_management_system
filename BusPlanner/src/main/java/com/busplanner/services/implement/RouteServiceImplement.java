/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.component.DistanceMatrixService;
import com.busplanner.dto.RouteDto;
import com.busplanner.dto.RouteSuggestion;
import com.busplanner.dto.StopDto;
import com.busplanner.pojo.Buses;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Routestops;
import com.busplanner.pojo.Stops;
import com.busplanner.repositories.BusRepository;
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
    @Autowired
    private BusRepository busRepository;

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
    // Tìm tối đa 2 Route (tức là chuyển tiếp chỉ 1 lần)
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
                RouteDto startRouteDto = new RouteDto(startRoute.getRouteId(), startRoute.getRouteName(), startRoute.getStartPoint(), startRoute.getEndPoint());

                routeSuggestions.add(new RouteSuggestion(startPoint, endPoint, startRouteDto,
                        nearestStartStopDto, null, nearestEndStopDto, null));
            }
        }

        // 4. Tìm tuyến chuyển tiếp (nếu không có tuyến trực tiếp)
        if (routeSuggestions.isEmpty()) {
            // Tìm các điểm dừng trung gian có thể đổi tuyến: 
            /* Duyệt các *Route đi qua Stop gần điểm đi* để lấy các Stop 
                mà các Stop này sẽ là "ứng cử viên" để làm Stop trung gian
             */
            for (Routes startRoute : startRoutes) {
                /* stopsOnStartRoute chính là danh sách các Stop có thể làm Stop chuyển tiếp
                vì Stop chuyển tiếp chắc chắn phải thuộc Route đang đi
                 */
                List<Stops> stopsOnStartRoute = new ArrayList<>();
                List<Routestops> routeStopList = routeStopRepository.getRouteStopsByRouteId(startRoute.getRouteId());
                for (Routestops rs : routeStopList) {
                    stopsOnStartRoute.add(rs.getStopId());
                }

                // Tìm Route chuyển tiếp
                /* Duyệt qua các Stop trên */
                for (Stops stop : stopsOnStartRoute) {
                    /* Với mỗi *Stop có thể là Stop trung gian*, tìm các Route đi qua nó, gọi là transferRoutes */
                    List<Routes> transferRoutes = routeRepository.findByStopId(stop.getStopId());
                    /* duyệt qua danh sách transferRoutes và kiểm tra xem liệu có tuyến đường nào trong danh sách này 
                    cũng nằm trong endRoutes (danh sách các tuyến đường đi qua điểm dừng gần điểm đến) hay không
                    Nếu có, điều này nghĩa là đã tìm thấy một tuyến đường mà người dùng có thể chuyển tiếp 
                    từ startRoute sang một transferRoute khác tại điểm dừng stop để đến được điểm đến (endPoint)
                     */
                    for (Routes transferRoute : transferRoutes) {
                        if (endRoutes.contains(transferRoute)) {
                            StopDto nearestStartStopDto = new StopDto(nearestStartStop.getStopId(), nearestStartStop.getStopName(), nearestStartStop.getLatitude(), nearestStartStop.getLongitude(), nearestStartStop.getAddress());
                            StopDto nearestEndStopDto = new StopDto(nearestEndStop.getStopId(), nearestEndStop.getStopName(), nearestEndStop.getLatitude(), nearestEndStop.getLongitude(), nearestEndStop.getAddress());
                            StopDto stopDto = new StopDto(stop.getStopId(), stop.getStopName(), stop.getLatitude(), stop.getLongitude(), stop.getAddress());
                            RouteDto startRouteDto = new RouteDto(startRoute.getRouteId(), startRoute.getRouteName(), startRoute.getStartPoint(), startRoute.getEndPoint());
                            RouteDto transferRouteDto = new RouteDto(transferRoute.getRouteId(), transferRoute.getRouteName(), transferRoute.getStartPoint(), transferRoute.getEndPoint());
                            routeSuggestions.add(
                                    new RouteSuggestion(startPoint, endPoint, startRouteDto,
                                            nearestStartStopDto, stopDto, nearestEndStopDto, transferRouteDto)
                            );
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

            //Cộng tổng khoảng cách và thời gian
            suggestion.setDistanceTotal(suggestion.getWalkingDistanceToStartStop()
                    + suggestion.getBusDistance()
                    + suggestion.getWalkingDistanceToEndPoint()
            );
            suggestion.setDurationTotal(suggestion.getWalkingTimeToStartStop()
                    + suggestion.getBusTime()
                    + suggestion.getWalkingTimeToEndPoint()
            );

            //Lấy các Bus cho startRoute và transferRoute
            List<Buses> busesForStartRoute = busRepository.getBusesByRouteId(suggestion.getStartRoute().getRouteId());
            List<Buses> busesForTransferRoute = null;

            // Kiểm tra nếu có tuyến chuyển tiếp
            if (suggestion.getTransferRoute() != null) {
                busesForTransferRoute = busRepository.getBusesByRouteId(suggestion.getTransferRoute().getRouteId());
            }
            suggestion.setBusesForStartRoute(busesForStartRoute);
            suggestion.setBusesForTransferRoute(busesForTransferRoute);
        }
        return routeSuggestions;
    }

}
