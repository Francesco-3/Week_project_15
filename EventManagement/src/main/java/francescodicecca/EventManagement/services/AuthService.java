package francescodicecca.EventManagement.services;

import francescodicecca.EventManagement.entities.User;
import francescodicecca.EventManagement.exceptions.UnauthorizedException;
import francescodicecca.EventManagement.secutiry.JWTTools;
import francescodicecca.EventManagement.payload.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

   public String checkCredentialsAndGenerateToken(LoginDTO body) {
        User found = this.userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.creteToken(found);
        } else {
            throw  new UnauthorizedException("Credenziali errate!");
        }
   }
}
