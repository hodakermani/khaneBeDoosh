package khaneBeDoosh.security;

import khaneBeDoosh.domain.Individual;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by nafise on 14/04/2018.
 */

@Component
public class TokenHelper {

    public static String generateToken(String username) {
        return Jwts.builder()
                .setIssuer( "khaneBeDoosh" )
                .setSubject(username)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .signWith( SignatureAlgorithm.HS256, "mozi-amoo" )
                .compact();
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("mozi-amoo")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public boolean validateToken(String token, Individual user) {
        final String username = getUsernameFromToken(token);
        final Date createdDate = getIssuedAtDateFromToken(token);
        Date currentDate = new Date();
        return (username != null && username.equals(user.getUsername()) && currentDate.after(createdDate));
    }

    public String getToken( HttpServletRequest request ) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        else if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}