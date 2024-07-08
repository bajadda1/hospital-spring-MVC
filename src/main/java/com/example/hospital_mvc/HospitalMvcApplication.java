package com.example.hospital_mvc;

import com.example.hospital_mvc.entities.AppUser;
import com.example.hospital_mvc.entities.Patient;
import com.example.hospital_mvc.repositories.PatientRepository;
import com.example.hospital_mvc.services.AppUserServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalMvcApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner(PatientRepository patientRepository, AppUserServiceImp appUserServiceImp) {
        return args -> {
            Stream.of("ahmed", "khadija", "youssef", "ahmed", "khadija", "youssef", "mohamed", "yaakoub", "yaakoub", "yaakoub", "Fatima", "Fatima", "Abdo", "Rajaa", "Safaa")
                    .forEach(name -> {
                        Patient patient = new Patient();
                        patient.setName(name);
                        patient.setSick(Math.random() > 0.5);
                        patient.setScore((int) Math.floor(66));
                        patient.setDateOfBirth(new Date());
                        patientRepository.save(patient);
                    });
            appUserServiceImp.addRole("USER");
            appUserServiceImp.addRole("ADMIN");
            Stream.of("user1", "user2", "admin")
                    .forEach(user -> {
                        appUserServiceImp.addNewUser(user, user + "@gmail.com", "123", "123");
                        switch (user) {
                            case "admin": {
                                appUserServiceImp.addRoleToUser(user, "USER");
                                appUserServiceImp.addRoleToUser(user, "ADMIN");
                                break;
                            }
                            default: {
                                appUserServiceImp.addRoleToUser(user, "USER");
                                break;
                            }
                        }
                    });
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
