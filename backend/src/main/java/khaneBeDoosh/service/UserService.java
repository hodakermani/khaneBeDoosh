package khaneBeDoosh.service;

import khaneBeDoosh.domain.*;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    Individual findByUsername(String username) throws SQLException;
    List<Individual> findAll() throws SQLException;
}
