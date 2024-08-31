/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Admin
 */
@WebFilter("/*") // Áp dụng cho tất cả các URL
public class CustomCharacterEncodingFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        // Đặt mã hóa UTF-8 cho yêu cầu
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Tiếp tục xử lý các bộ lọc khác trong chuỗi
        chain.doFilter(request, response);
    }
}
