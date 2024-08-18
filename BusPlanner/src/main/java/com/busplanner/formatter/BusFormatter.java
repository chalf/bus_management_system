/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.formatter;

import com.busplanner.pojo.Buses;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class BusFormatter implements Formatter<Buses>{

    @Override
    public String print(Buses object, Locale locale) {
        return String.valueOf(object.getBusId());
    }

    @Override
    public Buses parse(String text, Locale locale) throws ParseException {
        Buses bus = new Buses();
        bus.setBusId(Integer.valueOf(text));
        return bus;
    }
    
}
