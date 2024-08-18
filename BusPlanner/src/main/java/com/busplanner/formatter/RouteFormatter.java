/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.formatter;

import com.busplanner.pojo.Routes;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class RouteFormatter implements Formatter<Routes>{

    @Override
    public String print(Routes object, Locale locale) {
        return String.valueOf(object.getRouteId());
    }

    @Override
    public Routes parse(String text, Locale locale) throws ParseException {
        Routes route = new Routes();
        route.setRouteId(Integer.valueOf(text));
        return route;

    }
    
    
}
