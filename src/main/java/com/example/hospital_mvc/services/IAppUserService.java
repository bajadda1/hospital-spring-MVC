package com.example.hospital_mvc.services;

import com.example.hospital_mvc.entities.AppRole;
import com.example.hospital_mvc.entities.AppUser;

public interface IAppUserService {
    AppUser addNewUser(String username,String email,String password,String confirmPassword);
    AppRole addRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUserName(String username);
}
