package khaneBeDoosh.domain;

import khaneBeDoosh.data.HouseRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by nafise on 20/02/2018.
 */
public class Individual extends User implements UserDetails {

    private String phone;
    private int balance;
    private String username;
    private String password;

    public Individual(){
        super("");
    }

    public Individual(String _name, String _username, String _password, int _balance) {
        super(_name);
        this.balance = _balance;
        this.username = _username;
        this.password = _password;
    }

    public String getPhone() {
        return phone;
    }

    public int getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void addBalance(int _balance) {
        this.balance += _balance;
    }

    public void setBalance(int balance) { this.balance = balance; }

}
