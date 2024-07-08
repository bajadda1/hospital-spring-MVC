package com.example.hospital_mvc.repositories;

import com.example.hospital_mvc.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRole(String role);
}
