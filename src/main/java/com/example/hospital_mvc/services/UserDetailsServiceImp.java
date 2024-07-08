package com.example.hospital_mvc.services;

import com.example.hospital_mvc.entities.AppRole;
import com.example.hospital_mvc.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private AppUserServiceImp appUserServiceImp;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=this.appUserServiceImp.loadUserByUserName(username);
        if (appUser==null) throw new UsernameNotFoundException(String.format("User %s not found",username));
        List<AppRole> roles=appUser.getRoles();

        List<String> rolesStr=roles.stream().map(AppRole::getRole).toList();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities=roles.stream().map(role->new SimpleGrantedAuthority(role.getRole())).toList();

        return User
                .builder()
                .username(appUser.getUserName())
                .password(appUser.getUserPassword())
                .authorities(simpleGrantedAuthorities)
//                .roles(String.valueOf(rolesStr))
                .build();
    }
}
