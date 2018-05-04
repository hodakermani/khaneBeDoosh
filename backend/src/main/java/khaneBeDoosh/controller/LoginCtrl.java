package khaneBeDoosh.controller;

import khaneBeDoosh.domain.*;
import khaneBeDoosh.security.TokenHelper;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LoginCtrl {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Message addHouse (@RequestBody Individual user) throws SQLException {
        Boolean success = false;
        String msg = "";
        String jwtToken = "";

        User userRequested = App.getRequestedUser(user.getUsername(), user.getPassword());
        if (userRequested != null)
        {
            jwtToken = TokenHelper.generateToken(user.getUsername());
            msg = jwtToken;
            success = true;
        }
        else {
            msg = "wrong username or password.";
        }
        return new Message(counter.incrementAndGet(), success, msg);
    }
}
