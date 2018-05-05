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

        String username = Utility.convertToSafeString(user.getUsername());
        String password = Utility.convertToSafeString(user.getPassword());

        User userRequested = App.getRequestedUser(username, password);
        if (userRequested != null)
        {
            jwtToken = TokenHelper.generateToken(username);
            msg = jwtToken;
            success = true;
        }
        else {
            msg = "wrong username or password.";
        }
        return new Message(counter.incrementAndGet(), success, msg);
    }
}
