/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.component;

import com.busplanner.configs.GoogleMapsConfig;
import java.io.IOException;
import org.springframework.stereotype.Component;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author Admin
 */
@Component
//Distance Matrix API của GgMap tính toán khoảng cách và thời gian thực tế
public class DistanceMatrixService {
    OkHttpClient client = new OkHttpClient();
    
    public String calculate(String source, String destination) throws IOException {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source + "&destinations=" + destination + "&key=" + GoogleMapsConfig.API_KEY;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
