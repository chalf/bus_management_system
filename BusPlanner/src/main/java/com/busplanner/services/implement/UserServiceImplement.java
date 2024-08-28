/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.busplanner.services.implement;

import com.busplanner.dto.AddUserDto;
import com.busplanner.dto.UpdateUserDto;
import com.busplanner.pojo.Users;
import com.busplanner.repositories.UserRepository;
import com.busplanner.services.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userDetailsService")
public class UserServiceImplement implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Users retrieveUserByUsername(String username) {
        return this.userRepository.retrieveUserByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public AddUserDto addUser(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Users addedUser = this.userRepository.addUser(user);
        return new AddUserDto(addedUser.getUserId(), addedUser.getUsername(),
                addedUser.getEmail(), addedUser.getFullName(), addedUser.getAvatarUrl(),
                addedUser.getCreatedAt(), addedUser.getUpdatedAt());
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepository.authUser(username, password);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid User!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public Users getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public List<Users> getListUser(Map<String, String> params) {
        return userRepository.getListUser(params);
    }

    @Override
    public boolean updateUser(UpdateUserDto userData) {
        String username = userData.getUsername();
        if(this.existsByUsername(username) == false){
            return false;
        }
        boolean flag = false;
        Users user = this.getUserByUsername(username);
        if(userData.getPassword() != null){
            user.setPassword(bCryptPasswordEncoder.encode(userData.getPassword()));
            flag = true;
        }
        if(userData.getEmail()!= null){
            user.setEmail(userData.getEmail());
            flag = true;
        }
        if(this.existsByEmail(userData.getEmail())){
            return false;
        }
        if(userData.getFullName() != null){
            user.setFullName(userData.getFullName());
            flag = true;
        }
        if (userData.getFile() != null){
            Map imageInfo;
            try {
                imageInfo = cloudinary.uploader().upload(userData.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatarUrl((String) imageInfo.get("secure_url"));
                flag = true;
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(flag){ //mục đích là khi có ít nhất 1 trường dc cập nhật thì mới gán giá trị cho updatedAt
            // Gán giá trị cho updatedAt
            user.setUpdatedAt(new Date());
        }
        return this.userRepository.updateUser(user);
    }
    
}
