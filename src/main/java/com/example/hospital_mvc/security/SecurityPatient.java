package com.example.hospital_mvc.security;

import com.example.hospital_mvc.services.UserDetailsServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityPatient {

    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImp userDetailsServiceImp;

//    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        UserDetails ahmed= User.
                builder()
                .username("ahmed")
                .password(this.passwordEncoder.encode("123"))
                .roles("USER")
                .build();
        UserDetails khadija= User.
                builder()
                .username("khadija")
                .password(this.passwordEncoder.encode("123"))
                .roles("USER","ADMIN")
                .build();
        return new InMemoryUserDetailsManager(ahmed,khadija);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        );

        httpSecurity.formLogin(form ->
                form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
        );


        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.userDetailsService(this.userDetailsServiceImp);

        httpSecurity.exceptionHandling(access->{
            access.accessDeniedPage("/accessDenied");
        });
        //disable csrf
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
