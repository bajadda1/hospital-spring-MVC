package com.example.hospital_mvc.repositories;

import com.example.hospital_mvc.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUserName(String username);
}
