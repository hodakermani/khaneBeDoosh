package khaneBeDoosh.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import khaneBeDoosh.data.UserRepository;
import khaneBeDoosh.domain.*;
import khaneBeDoosh.security.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nafise on 06/04/2018.
 */

@RestController
public class LoginCtrl {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public Message addHouse (@RequestBody Individual user) throws SQLException {

        Boolean success = false;
        String msg = "";
        String jwtToken = "";

        User userRequested = App.getLoginUser(user.getUsername(), user.getPassword());
        if (userRequested != null)
        {
            jwtToken = TokenHelper.generateToken(user.getUsername());
//                    Jwts.builder().setSubject(userRequested.getName()).claim("roles", "user").setIssuedAt(new Date())
//                    .setIssuer("khaneBeDoosh").signWith(SignatureAlgorithm.HS256, "mozi-amoo").compact();
            msg = jwtToken;
            success = true;
        }
        else {
            msg = "please enter correct username or password.";
        }

        return new Message(counter.incrementAndGet(), success, msg);
    }
}

//    TokenHelper tokenHelper;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(
//            @RequestBody JwtAuthenticationRequest authenticationRequest,
//            HttpServletResponse response,
//            Device device
//    ) throws AuthenticationException, IOException {
//
//        // Perform the security
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authenticationRequest.getUsername(),
//                        authenticationRequest.getPassword()
//                )
//        );
//
//        // Inject into security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // token creation
//        User user = (User)authentication.getPrincipal();
//        String jws = tokenHelper.generateToken(user.getUsername());
//        // Return the token
//        return ResponseEntity.ok(jws);
//    }
