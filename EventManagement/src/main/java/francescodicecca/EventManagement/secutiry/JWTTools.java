package francescodicecca.EventManagement.secutiry;

import francescodicecca.EventManagement.entities.User;
import francescodicecca.EventManagement.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import java.util.Date;

@Component
public class JWTTools {
   // @Value("${jwt.secret}")
    private String secret;

    public String creteToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
        } catch (Exception ex) {
            throw new UnauthorizedException("Ci sono stati errori nel token! Effettua di nuovo il login!");
        }
    }
}
