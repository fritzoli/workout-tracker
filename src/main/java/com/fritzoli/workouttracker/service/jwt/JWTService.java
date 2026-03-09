package com.fritzoli.workouttracker.service.jwt;

import com.fritzoli.workouttracker.model.user.IUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private String secretKey = "";

    public JWTService() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getUrlEncoder().withoutPadding().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String generateLoginToken(String username) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Duration.ofMinutes(15))))
                .signWith(getKey())
                .compact();
    }

    public String generateMailVerificationToken(String username, String email) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("purpose", "email_verification")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Duration.ofMinutes(15))))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String extractPurpose(String token) {
        return extractClaim(token, claims -> claims.get("purpose", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, IUser userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public void validateVerifyToken(String token) {
        if (isTokenExpired(token))
            throw new RuntimeException("This link has expired");

        if (!extractPurpose(token).equals("email_verification"))
            throw new RuntimeException("This token is not for email verification");
    }
}
