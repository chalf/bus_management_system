/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.component;

import com.busplanner.configs.GoogleMapsConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
//Geocoding của Google Maps API để ánh xạ địa chỉ thành tọa độ
@Component
public class GeocodingService {

    OkHttpClient client = new OkHttpClient();

    public Map<String, String> geocoding(String address) throws IOException {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không hợp lệ");
        }
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + GoogleMapsConfig.API_KEY;
    //  String url = "https://nominatim.openstreetmap.org/search?q=" + address + "&format=json";
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() == null) {

            throw new IOException("Không thể nhận được phản hồi từ server");
        }
        String jsonResponse = response.body().string();
        return extractLatLongFromJsonGoogle(jsonResponse);

    }

    ObjectMapper objectMapper = new ObjectMapper(); // Tạo ObjectMapper để xử lý JSON

    private Map<String, String> extractLatLongFromJsonGoogle(String json) throws IOException {
        Map<String, String> coordinates = new HashMap<>();

        // Phân tích chuỗi JSON để lấy giá trị latitude và longitude
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode resultsNode = jsonNode.get("results");

        if (resultsNode.isArray() && resultsNode.size() > 0) {
            JsonNode locationNode = resultsNode.get(0)
                    .get("geometry")
                    .get("location");
            String latitude = locationNode.get("lat").asText();
            String longitude = locationNode.get("lng").asText();

            coordinates.put("latitude", latitude);
            coordinates.put("longitude", longitude);
        }

        return coordinates;
    }

    //từ json mà Nominatim trả về phân tách ra lấy 2 giá trị lat và long 
    private Map<String, String> extractLatLongFromJsonNominatim(String json) throws IOException {
        Map<String, String> coordinates = new HashMap<>();
        // Phân tích chuỗi JSON để lấy giá trị latitude và longitude
        JsonNode jsonNode = objectMapper.readTree(json);

        // Kiểm tra nếu có kết quả
        if (jsonNode.isArray() && jsonNode.size() > 0) {
            JsonNode firstResult = jsonNode.get(0);
            String latitude = firstResult.has("lat") ? firstResult.get("lat").asText() : null;
            String longitude = firstResult.has("lon") ? firstResult.get("lon").asText() : null;

            if (latitude != null && longitude != null) {
                coordinates.put("latitude", latitude);
                coordinates.put("longitude", longitude);
            }
        }
        return coordinates;
        // Trả về map, dù có thể rỗng thay vì null để tránh NullPointerException ở các phương thức khác
    }

}
