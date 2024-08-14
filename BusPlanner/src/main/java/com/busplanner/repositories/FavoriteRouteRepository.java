/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.busplanner.repositories;

import com.busplanner.pojo.Favoriteroutes;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface FavoriteRouteRepository {
    List<Favoriteroutes> getFavoriteRoutesByUserId(int userId);
    Favoriteroutes addOrUpdateFavoriteRoute(Favoriteroutes favoriteroute);
    Favoriteroutes getFavoriteRouteById(int favoriteId);
    void deleteFavoriteRoute(int favoriteId);
    List<Favoriteroutes> getAllFavoriteRoutes();
}
