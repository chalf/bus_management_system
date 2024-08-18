/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.pojo.Favoriteroutes;
import com.busplanner.repositories.FavoriteRouteRepository;
import com.busplanner.services.FavoriteRouteService;
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
    public List<Favoriteroutes> getFavoriteRoutesByUserId(int userId) {
        return favoriteRouteRepository.getFavoriteRoutesByUserId(userId);
    }

    @Override
    @Transactional
    public Favoriteroutes addOrUpdateFavoriteRoute(Favoriteroutes favoriteroute) {
        return favoriteRouteRepository.addOrUpdateFavoriteRoute(favoriteroute);
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
