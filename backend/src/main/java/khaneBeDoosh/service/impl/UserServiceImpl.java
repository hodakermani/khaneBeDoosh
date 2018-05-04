package khaneBeDoosh.service.impl;

import khaneBeDoosh.data.*;
import khaneBeDoosh.domain.*;
import khaneBeDoosh.service.*;

import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Individual findByUsername( String username ) throws UsernameNotFoundException, SQLException {
        return UserRepository.findUserByUsername( username );
    }

    public List<Individual> findAll() throws AccessDeniedException, SQLException {
        return UserRepository.findAll();
    }
}
