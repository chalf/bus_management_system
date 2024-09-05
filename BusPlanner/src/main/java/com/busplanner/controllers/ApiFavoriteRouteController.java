/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.dto.FavoriteRouteDto;
import com.busplanner.pojo.Favoriteroutes;
import com.busplanner.pojo.Routes;
import com.busplanner.pojo.Users;
import com.busplanner.services.FavoriteRouteService;
import com.busplanner.services.RouteService;
import com.busplanner.services.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiFavoriteRouteController {
    @Autowired
    private FavoriteRouteService favoriteRouteService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private UserService userService;
    
    @PostMapping(path = "/user/current-user/like/{routeId}")
    public ResponseEntity<FavoriteRouteDto> like(Principal user, @PathVariable(value = "routeId") int routeId){
        Routes route =  routeService.getRouteById(routeId);
        Users currentUser = userService.getUserByUsername(user.getName());
        Favoriteroutes favRoute = new Favoriteroutes();
        favRoute.setRouteId(route);
        favRoute.setUserId(currentUser);
        FavoriteRouteDto favRouteDto = favoriteRouteService.addOrUpdateFavoriteRoute(favRoute);
        return new ResponseEntity<>( favRouteDto, HttpStatus.CREATED);
    }
    
    @DeleteMapping(path = "/user/current-user/unlike/{favoriteRouteId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Successfully")
    public void unlike(Principal user, @PathVariable(value = "favoriteRouteId") int favoriteRouteId){
        Users currentUser = userService.getUserByUsername(user.getName());
        Favoriteroutes favRoute = favoriteRouteService.getFavoriteRouteById(favoriteRouteId);
        if(currentUser.equals(favRoute.getUserId()) == false){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not " + favRoute.getUserId().getUsername());
        }
        try {
            favoriteRouteService.deleteFavoriteRoute(favoriteRouteId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST   , "Delete failed!");
        }
        
    }
    
    @GetMapping(path = "/user/current-user/liked-routes")
    public ResponseEntity<List<FavoriteRouteDto>> getFavRoute(Principal user){
        Users currentUser = userService.getUserByUsername(user.getName());
        List<FavoriteRouteDto> favRouteListDto = favoriteRouteService.getFavoriteRoutesByUserId(currentUser.getUserId());
        return new ResponseEntity<>(favRouteListDto, HttpStatus.OK);
    }
    
}
