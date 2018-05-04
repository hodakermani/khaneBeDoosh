package khaneBeDoosh.security;

import khaneBeDoosh.domain.Individual;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by nafise on 14/04/2018.
 */

@Component
public class TokenHelper {

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

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

    public Date getExpirationDateFromToken(String token) {
        Date expiresAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiresAt = claims.getExpiration();
        } catch (Exception e) {
            expiresAt = null;
        }
        return expiresAt;
    }

//    public String refreshToken(String token) {
//        String refreshedToken;
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            claims.setIssuedAt(new Date());
//            refreshedToken = Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(generateExpirationDate())
//                .signWith( SIGNATURE_ALGORITHM, SECRET )
//                .compact();
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setIssuer( "khaneBeDoosh" )
                .setSubject(username)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .signWith( SignatureAlgorithm.HS256, "mozi-amoo" )
                .compact();
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

    private Date generateExpirationDate() {
        long expiresIn = 10000; //TODO:change me
        return new Date(new Date().getTime() + expiresIn * 1000);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        Individual user = (Individual) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (
                username != null &&
                username.equals(userDetails.getUsername())
        );
    }

    private Boolean isTokenNotExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        Date currentTime = new Date();
        return currentTime.before(expirationDate);
    }

    public String getToken( HttpServletRequest request ) {
        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getAuthHeaderFromHeader( HttpServletRequest request ) {
        return request.getHeader("Authorization");
    }

}