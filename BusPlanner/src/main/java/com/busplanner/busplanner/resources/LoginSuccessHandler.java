/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.busplanner.resources;

import com.busplanner.component.JwtService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author Admin
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public LoginSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
// Tạo token JWT sau khi đăng nhập thành công
        String token = jwtService.generateTokenLogin(authentication.getName());
        response.addHeader("Authorization", "Bearer " + token);

        // Chuyển hướng đến URL admin bao gồm context path
        response.sendRedirect(request.getContextPath() + "/admin/");
    }

}
