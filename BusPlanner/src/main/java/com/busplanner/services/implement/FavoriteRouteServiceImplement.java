/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.dto.FavoriteRouteDto;
import com.busplanner.dto.RouteDto;
import com.busplanner.pojo.Favoriteroutes;
import com.busplanner.repositories.FavoriteRouteRepository;
import com.busplanner.services.FavoriteRouteService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ASUS
 */
@Service
public class FavoriteRouteServiceImplement implements FavoriteRouteService{
    @Autowired
    private FavoriteRouteRepository favoriteRouteRepository;

    @Override
    @Transactional
    public List<FavoriteRouteDto> getFavoriteRoutesByUserId(int userId) {
        List<Favoriteroutes> favRouteList = favoriteRouteRepository.getFavoriteRoutesByUserId(userId);
        List<FavoriteRouteDto> favRouteListDto = new ArrayList<>();
        for(Favoriteroutes favRoute : favRouteList){
            RouteDto routeDto = new RouteDto(favRoute.getRouteId().getRouteId(),
                    favRoute.getRouteId().getRouteName(), 
                    favRoute.getRouteId().getStartPoint(),
                    favRoute.getRouteId().getEndPoint());
            FavoriteRouteDto favRouteDto = new FavoriteRouteDto(favRoute.getFavoriteId(), routeDto, favRoute.getUserId().getUsername());
            favRouteListDto.add(favRouteDto);
        }
        return favRouteListDto;
    }

    @Override
    @Transactional
    public FavoriteRouteDto addOrUpdateFavoriteRoute(Favoriteroutes favoriteroute) {
        Favoriteroutes fav = favoriteRouteRepository.addOrUpdateFavoriteRoute(favoriteroute);
        RouteDto routeDto = new RouteDto(fav.getRouteId().getRouteId(),
                    fav.getRouteId().getRouteName(), 
                    fav.getRouteId().getStartPoint(),
                    fav.getRouteId().getEndPoint());
        
        return new FavoriteRouteDto(fav.getFavoriteId(), routeDto, fav.getUserId().getUsername());
    }

    @Override
    @Transactional
    public Favoriteroutes getFavoriteRouteById(int favoriteId) {
        return favoriteRouteRepository.getFavoriteRouteById(favoriteId);
    }

    @Override
    @Transactional
    public void deleteFavoriteRoute(int favoriteId) {
        favoriteRouteRepository.deleteFavoriteRoute(favoriteId);
    }

    @Override
    public List<Favoriteroutes> getAllFavoriteRoutes() {
        return favoriteRouteRepository.getAllFavoriteRoutes();
    }
    
}
