package com.example.hospital_mvc.services;

import com.example.hospital_mvc.entities.AppRole;
import com.example.hospital_mvc.entities.AppUser;
import com.example.hospital_mvc.repositories.AppRoleRepository;
import com.example.hospital_mvc.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AppUserServiceImp implements IAppUserService{

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String email, String password, String confirmPassword) {
        AppUser appUser=this.appUserRepository.findByUserName(username);
        if(appUser != null){
            throw new RuntimeException("user already exists");
        }
        if(!confirmPassword.equals(password)){
            throw new RuntimeException("password not much");
        }
        appUser=AppUser
                .builder()
                .userID(UUID.randomUUID().toString())
                .userName(username)
                .userPassword(this.passwordEncoder.encode(password))
                .userEmail(email)
                .build();
        this.appUserRepository.save(appUser);
        return appUser;
    }

    @Override
    public AppRole addRole(String role) {
        AppRole appRole=this.appRoleRepository.findByRole(role);
        if(appRole!=null){
            throw new RuntimeException("role already exists");
        }

        appRole=AppRole
                .builder()
                .role(role)
                .build();

        return this.appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser=this.appUserRepository.findByUserName(username);
        if(appUser == null){
            throw new RuntimeException("user not exists");
        }
        AppRole appRole=this.appRoleRepository.findByRole(role);
        if(appRole == null){
            throw new RuntimeException("role not exists");
        }
        appUser.getRoles().add(appRole);
        this.appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser=this.appUserRepository.findByUserName(username);
        if(appUser == null){
            throw new RuntimeException("user not exists");
        }
        AppRole appRole=this.appRoleRepository.findByRole(role);
        if(appRole == null){
            throw new RuntimeException("role not exists");
        }
        appUser.getRoles().remove(appRole);
        this.appUserRepository.save(appUser);

    }

    @Override
    public AppUser loadUserByUserName(String username) {
        return this.appUserRepository.findByUserName(username);
    }
}
