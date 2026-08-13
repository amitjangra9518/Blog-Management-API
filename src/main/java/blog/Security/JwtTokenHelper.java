package blog.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(
                    "ThisIsMyVeryLongSecretKeyForJwtTokenGenerationAndValidation1234567890123456789"
                            .getBytes(StandardCharsets.UTF_8));


    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token,
                                   Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimFromToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // NOTE: rewritten to use the modern jjwt 0.12+ builder API
    // (claims/subject/issuedAt/expiration/signWith(Key)) to match
    // the parser side above. The previous version mixed this with
    // the deprecated setClaims()/setSubject()/signWith(Algorithm, Key)
    // API, which is inconsistent and can fail to compile depending
    // on the exact jjwt version on the classpath.
    private String doGenerateToken(Map<String, Object> claims,
                                   String subject) {

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(System.currentTimeMillis()
                                + JWT_TOKEN_VALIDITY * 1000L))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validationToken(String token,
                                   UserDetails userDetails) {

        final String username = getUserNameFromToken(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}