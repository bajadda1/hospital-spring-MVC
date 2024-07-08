package com.example.hospital_mvc.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    private String userID;
    @Column(unique = true)
    private String userName;
    private String userPassword;
    private String userEmail;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
