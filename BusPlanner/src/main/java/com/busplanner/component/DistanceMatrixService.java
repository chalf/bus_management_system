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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Admin
 */
@Component
//Distance Matrix API của GgMap tính toán khoảng cách và thời gian thực tế
public class DistanceMatrixService {

    OkHttpClient client = new OkHttpClient();

    public String sendRequest(String source, String destination, String mode) throws IOException {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json"
                + "?origins=" + source
                + "&destinations=" + destination
                + "&mode=" + mode
                + "&key=" + GoogleMapsConfig.API_KEY;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // Tạo một lớp để chứa kết quả bao gồm cả khoảng cách và thời gian
    public static class DistanceTimeResult {

        public final double distance;
        public final double duration;

        public DistanceTimeResult(double distance, double duration) {
            this.distance = distance;
            this.duration = duration;
        }
    }

    // Phương thức trích xuất khoảng cách từ phản hồi JSON
    private double extractDistance(JSONObject response) {
        if (response == null) {
            throw new IllegalArgumentException("Response object is null");
        }

        JSONArray rows = (JSONArray) response.get("rows");
        if (rows == null || rows.isEmpty()) {
            throw new IllegalStateException("No rows found in the response");
        }

        JSONObject firstRow = (JSONObject) rows.get(0);
        if (firstRow == null) {
            throw new IllegalStateException("First row is null");
        }

        JSONArray elements = (JSONArray) firstRow.get("elements");
        if (elements == null || elements.isEmpty()) {
            throw new IllegalStateException("No elements found in the first row");
        }

        JSONObject firstElement = (JSONObject) elements.get(0);
        if (firstElement == null) {
            throw new IllegalStateException("First element is null");
        }

        // Kiểm tra trạng thái của phần tử đầu tiên
        String status = (String) firstElement.get("status");
        if ("ZERO_RESULTS".equals(status)) {
            // Trả về giá trị khoảng cách mặc định là 0 khi không có kết quả
            return 0.0;
        }
        
        JSONObject distance = (JSONObject) firstElement.get("distance");
        if (distance == null) {
            throw new IllegalStateException("Distance object is null");
        }

        Object value = distance.get("value");
        if (value == null) {
            throw new IllegalStateException("Distance value is null");
        }

        // Chuyển đổi giá trị từ mét sang kilômét
        return Double.parseDouble(distance.get("value").toString()) / 1000.0;
    }

    // Phương thức trích xuất thời gian từ phản hồi JSON
    private double extractDuration(JSONObject response) {
        if (response == null) {
            throw new IllegalArgumentException("Response object is null");
        }

        JSONArray rows = (JSONArray) response.get("rows");
        if (rows == null || rows.isEmpty()) {
            throw new IllegalStateException("No rows found in the response");
        }

        JSONObject firstRow = (JSONObject) rows.get(0);
        if (firstRow == null) {
            throw new IllegalStateException("First row is null");
        }

        JSONArray elements = (JSONArray) firstRow.get("elements");
        if (elements == null || elements.isEmpty()) {
            throw new IllegalStateException("No elements found in the first row");
        }

        JSONObject firstElement = (JSONObject) elements.get(0);
        if (firstElement == null) {
            throw new IllegalStateException("First element is null");
        }

        // Kiểm tra trạng thái của phần tử đầu tiên
        String status = (String) firstElement.get("status");
        if ("ZERO_RESULTS".equals(status)) {
            // Trả về giá trị thời gian mặc định là 0 khi không có kết quả
            return 0.0;
        }
        
        JSONObject duration = (JSONObject) firstElement.get("duration");
        if (duration == null) {
            throw new IllegalStateException("Duration object is null");
        }

        Object value = duration.get("value");
        if (value == null) {
            throw new IllegalStateException("Duration value is null");
        }

        // Chuyển đổi giá trị từ giây sang phút
        return Double.parseDouble(duration.get("value").toString()) / 60.0;
    }

    // Phương thức tính toán cả khoảng cách và thời gian đi bộ
    public DistanceTimeResult calculateWalkingDetails(String origin, String destination) throws IOException, ParseException {
        String mode = "walking"; // Mode đi bộ.
        String responseString = sendRequest(origin, destination, mode);

        // Phân tích chuỗi JSON thành đối tượng JSONObject
        JSONObject response = (JSONObject) new JSONParser().parse(responseString);

        // Trích xuất cả khoảng cách và thời gian từ phản hồi JSON
        double distance = extractDistance(response);
        double duration = extractDuration(response);

        return new DistanceTimeResult(distance, duration);
    }

    // Phương thức tính toán cả khoảng cách và thời gian sử dụng phương tiện công cộng
    public DistanceTimeResult calculateTransitDetails(String origin, String destination) throws IOException, ParseException {
        String mode = "transit"; // Mode phương tiện công cộng.
        String responseString = sendRequest(origin, destination, mode);

        JSONObject response = (JSONObject) new JSONParser().parse(responseString);

        double distance = extractDistance(response);
        double duration = extractDuration(response);

        return new DistanceTimeResult(distance, duration);
    }

}
