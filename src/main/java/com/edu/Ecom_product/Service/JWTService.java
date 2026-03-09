package com.edu.Ecom_product.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

   //private static final String SECRET_KEY = "35fsecrettfuygiejrnfienfcjernvirni87594hfijrenckjenrfckjnrinti4uhiu5th6gijncekjn4iuthufni576r65etcvhv";

    @Value("${jwt.secret}")
    private String SECRET_KEY;

/* generating new secret key for each run if the application is restarted,
but we can also use a fixed secret key by uncommenting the above line and commenting the constructor
but for production environment we should use a fixed secret key and keep it safe and secure, and
for development environment we can use a random secret key for each run to avoid security issues.

    public JWTService() {
        try {
            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk=keyGenerator.generateKey();
            SECRET_KEY= Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
*/

    public String generateToken(String userName) {
        Map<String, Object> claim=new HashMap<>();
        return Jwts
                .builder()
                .claims(claim)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
            return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final  Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // public helper to expose expiration date for logout/blacklist logic
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userName=extractUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
         Date expirationDate=extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
}

