package khaneBeDoosh.security.auth;

import khaneBeDoosh.data.UserRepository;
import khaneBeDoosh.domain.App;
import khaneBeDoosh.domain.Individual;
import khaneBeDoosh.security.TokenHelper;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static Logger logger = Logger.getLogger(TokenAuthenticationFilter.class.getName());

    private TokenHelper tokenHelper;

    private UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenHelper tokenHelper, UserDetailsService userDetailsService) {
        this.tokenHelper = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String username;
        String authToken = tokenHelper.getToken(request);

        if (authToken != null) {
            // get username from token
            username = tokenHelper.getUsernameFromToken(authToken);
            if (username != null) {
                // get user
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    Individual user = UserRepository.findUserByUsername(username);
                    if (tokenHelper.validateToken(authToken, user)) {
                        // create authentication
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        App.setLoginUser(user);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request, response);
    }

}