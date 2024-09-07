/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.component;

import com.busplanner.dto.GoogleResponseDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


/**
 *
 * @author Admin
 */
@Component
public class GoogleAuth {

    public static String GOOGLE_CLIENT_ID = "1040741028275-nr3qljgsdvcitatesfmlf0snomtf0vim.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8080/BusPlanner/api/auth/google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

//    public String getToken(final String code) throws ClientProtocolException, IOException {
//        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
//                .bodyForm(Form.form().add("client_id", GOOGLE_CLIENT_ID)
//                        .add("client_secret", GOOGLE_CLIENT_SECRET)
//                        .add("redirect_uri", GOOGLE_REDIRECT_URI).add("code", code)
//                        .add("grant_type", GOOGLE_GRANT_TYPE).build())
//                .execute().returnContent().asString();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = mapper.readTree(response).get("access_token");
//        return node.textValue();
//    }
//
//    public GoogleResponseDto getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
//        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
//        String response = Request.Get(link).execute().returnContent().asString();
//        ObjectMapper mapper = new ObjectMapper();
//        GoogleResponseDto googlePojo = mapper.readValue(response, GoogleResponseDto.class);
//        System.out.println(googlePojo);
//        return googlePojo;
//    }
//
//    public UserDetails buildUser(GoogleResponseDto googlePojo) {
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        UserDetails userDetail = new User(googlePojo.getEmail(),
//                "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//        return userDetail;
//    }
    
    public String getToken(final String code) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(GOOGLE_LINK_GET_TOKEN);

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", GOOGLE_CLIENT_ID));
        urlParameters.add(new BasicNameValuePair("client_secret", GOOGLE_CLIENT_SECRET));
        urlParameters.add(new BasicNameValuePair("redirect_uri", GOOGLE_REDIRECT_URI));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("grant_type", GOOGLE_GRANT_TYPE));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = httpClient.execute(post);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result.toString()).get("access_token");
        return node.textValue();
    }

    public GoogleResponseDto getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(GOOGLE_LINK_GET_USER_INFO + accessToken);

        HttpResponse response = httpClient.execute(get);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        GoogleResponseDto googlePojo = mapper.readValue(result.toString(), GoogleResponseDto.class);
        System.out.println(googlePojo);
        return googlePojo;
    }

    public UserDetails buildUser(GoogleResponseDto googlePojo) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CITIZEN"));
        UserDetails userDetail = new User(googlePojo.getEmail(),
                "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return userDetail;
    }
}
