package francescodicecca.EventManagement.controller;

import francescodicecca.EventManagement.entities.User;
import francescodicecca.EventManagement.exceptions.ValidationException;
import francescodicecca.EventManagement.payloads.LoginDTO;
import francescodicecca.EventManagement.payloads.LoginResponseDTO;
import francescodicecca.EventManagement.payloads.NewUserDTO;
import francescodicecca.EventManagement.services.AuthService;
import francescodicecca.EventManagement.services.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldError()
                    .stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.userService.save(payload);
    }
}
