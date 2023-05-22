package demo.controllers;

import demo.DTO.LoginRequestDTO;
import demo.DTO.MessageResponse;
import demo.DTO.SignupRequestDTO;
import demo.domain.entities.Role;
import demo.domain.entities.User;
import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;
import demo.domain.valueobjects.UserId;
import demo.repositories.RoleRepository;
import demo.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static demo.domain.valueobjects.ERole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
class AuthControllerTest {

    @MockBean
    private User user;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthController authController;


    @Test
    void registerUserSuccess() {
        //Arrange
        long id = 123456789;
        String username = "username";
        String firstName = "First";
        String lastName = "Last";
        String email = RandomString.make() + "@email.com";
        String password = "Password1";
        SignupRequestDTO dto = new SignupRequestDTO(id, username, firstName, lastName, email, password);

        RoleId roleId = new RoleId(12345L);
        ERole eRole = ERole.ROLE_USER;
        Role role = new Role(roleId, eRole);
        when(roleRepository.findByName(ROLE_USER)).thenReturn(Optional.of(role));
        //Act
        ResponseEntity<?> result = authController.registerUser(dto);
        ResponseEntity<?> expected = ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        //Assert
        assertEquals(expected, result);
    }

    @Test
    void registerUserFail_usernameAlreadyExits() {
        //Arrange
        long id = 123456789;
        String username = "username";
        String firstName = "First";
        String lastName = "Last";
        String email = RandomString.make() + "@email.com";
        String password = "Password1";
        SignupRequestDTO dto = new SignupRequestDTO(id, username, firstName, lastName, email, password);

        when(userRepository.existsByUsername(username)).thenReturn(true);
        //Act
        ResponseEntity<?> result = authController.registerUser(dto);
        ResponseEntity<?> expected = ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
        //Assert
        assertEquals(expected, result);
    }

    @Test
    void registerUserFail_emailAlreadyExits() {
        //Arrange
        long id = 123456789;
        String username = "username";
        String firstName = "First";
        String lastName = "Last";
        String email = RandomString.make() + "@email.com";
        String password = "Password1";
        SignupRequestDTO dto = new SignupRequestDTO(id, username, firstName, lastName, email, password);

        when(userRepository.existsByEmail(email)).thenReturn(true);
        //Act
        ResponseEntity<?> result = authController.registerUser(dto);
        ResponseEntity<?> expected = ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Email is already in use!"));
        //Assert
        assertEquals(expected, result);
    }

    @Test
    void authenticateUser() {
        //Arrange
        String username = "username";
        String password = "123456789";
        String encodedPassword = encoder.encode(password);

        LoginRequestDTO dto = new LoginRequestDTO(username, password);

        ///UserDetailsImpl - Mocks user and repository
        RoleId id = new RoleId(12345L);
        ERole eRole = ROLE_USER;
        Role role = new Role(id, eRole);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        when(user.getRoles()).thenReturn(roles);

        UserId userID = new UserId();
        when(user.getId()).thenReturn(userID);
        when(user.getUsername()).thenReturn(username);
        when(user.getPassword()).thenReturn(encodedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        //Act
        ResponseEntity<?> result = authController.authenticateUser(dto);
        //Assert
        assertNotNull(result);
    }

}