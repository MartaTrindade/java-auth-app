package demo.services;

import demo.DTO.SignupRequestDTO;
import demo.domain.entities.Role;
import demo.domain.entities.User;
import demo.domain.valueobjects.ERole;
import demo.repositories.RoleRepository;
import demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;


    public void registerUser(SignupRequestDTO signUpRequestDTO) {
        // Create new user's account
        User user = new User(signUpRequestDTO.getId(),
                signUpRequestDTO.getFirstName(),
                signUpRequestDTO.getLastName(),
                signUpRequestDTO.getEmail(),
                signUpRequestDTO.getUsername(),
                encoder.encode(signUpRequestDTO.getPassword())
        );
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userService.createAndSaveUser(signUpRequestDTO.getId(),
                signUpRequestDTO.getFirstName(),
                signUpRequestDTO.getLastName(),
                signUpRequestDTO.getEmail(),
                signUpRequestDTO.getUsername(),
                encoder.encode(signUpRequestDTO.getPassword())
        );
    }

    public void updateUserRole(long userId, String role) {
        Set<Role> roles = new HashSet<>();
        switch (role) {
            case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
                break;
            case "mod":
                Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
                break;
            default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
        }
        /*UserId userID = new UserId(userId);
        User user = userService.findById(userID);
        user.setRoles(roles);
        userRepository.save(user);*/
    }

}
