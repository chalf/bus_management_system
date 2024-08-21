/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.controllers;

import com.busplanner.component.JwtService;
import com.busplanner.pojo.LoginForm;
import com.busplanner.pojo.Users;
import com.busplanner.services.UserService;
import com.busplanner.validator.WebAppValidator;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
//    @Autowired
//    private WebAppValidator webAppValidator;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@ModelAttribute LoginForm login) {
        if (this.userService.authUser(login.getUsername(), login.getPassword()) == true) {
            String token = this.jwtService.generateTokenLogin(login.getUsername());

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>("Không tồn tại tài khoản hoặc mật khẩu không đúng", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping(path = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> details(Principal user) {
        // nếu ko có token --> 403 - Forbidden
        return new ResponseEntity<>(this.userService.retrieveUserByUsername(user.getName()), HttpStatus.OK);
    }

//    @InitBinder({"create", })
//    public void initBinder(WebDataBinder binder) {
//        binder.setValidator(webAppValidator);
//    }
    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @ModelAttribute Users userRegister,
            @RequestPart("file") MultipartFile file) {
        userRegister.setFile(file);
        //mặc định tạo user có role là citizen
        userRegister.setRole("citizen");
        Users user = this.userService.addUser(userRegister);
        
        if (user.getUserId() == Users.duplicateUsername())
            return new ResponseEntity<>( "Username đã tồn tại", HttpStatus.CONFLICT);
        if (user.getUserId() == Users.duplicateEmail())
            return new ResponseEntity<>("Email đã được sử dụng", HttpStatus.CONFLICT);
        
        return new ResponseEntity<>( user, HttpStatus.CREATED);
    }
}
