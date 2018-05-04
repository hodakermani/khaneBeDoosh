package khaneBeDoosh.service.impl;

import khaneBeDoosh.domain.Individual;
import khaneBeDoosh.data.UserRepository;

import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Individual user = null;
        try {
            user = UserRepository.findUserByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username " + username));
        } else {
            return user;
        }
    }
}
