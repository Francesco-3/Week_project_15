package francescodicecca.EventManagement.services;

import francescodicecca.EventManagement.entities.User;
import francescodicecca.EventManagement.entities.enums.UserRole;
import francescodicecca.EventManagement.exceptions.BadRequestException;
import francescodicecca.EventManagement.exceptions.NotFoundException;
import francescodicecca.EventManagement.payloads.NewUserDTO;
import francescodicecca.EventManagement.repositories.UsersRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return this.usersRepository.findAll(pageable);
    }

    public User save(NewUserDTO payload) {
        this.usersRepository.findByEmail(payload.email()).ifPresent(user -> {
                    throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
                }
        );

        User newUser = new User();
        newUser.setFullName(payload.full_name());
        newUser.setEmail(payload.email());
        newUser.setPassword(bcrypt.encode(payload.password()));
        newUser.setRole(UserRole.USER);

        User savedUser = this.usersRepository.save(newUser);

        log.info("L'utente con id: " + savedUser.getId() + " è stato salvato correttamente!");

        return savedUser;
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato"));
    }
}
