package demo.security.services;

import demo.domain.entities.Role;
import demo.domain.entities.User;
import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;
import demo.domain.valueobjects.UserId;
import demo.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
class UserDetailsServiceImplTest {

    @MockBean
    private User user;
    @MockBean
    private UserRepository UserRepository;
    @MockBean
    private UserDetails userDetails;
    @MockBean
    private UserDetailsImpl userDetailsImpl;
    @Autowired
    private UserDetailsServiceImpl serviceImpl;


    @BeforeEach
    void setup() {
        assertThat(serviceImpl).isNotNull();
    }

    @Test
    void loadUserByUsername_Success() {
        //Arrange
        RoleId id = new RoleId(12345L);
        ERole eRole = ERole.ROLE_USER;
        Role role = new Role(id, eRole);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        when(user.getRoles()).thenReturn(roles);

        UserId userID = new UserId();
        when(user.getId()).thenReturn(userID);
        String username = "PersonUsername";
        when(user.getUsername()).thenReturn(username);

        when(UserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        UserDetailsImpl expected = UserDetailsImpl.build(user);
        //Act
        UserDetails result = serviceImpl.loadUserByUsername(username);
        //Assert
        assertEquals(expected, result);
    }

    @Test
    void loadUserByUsername_Fail_userNotFound() {
        //Arrange
        String username = "PersonUsername";
        when(UserRepository.findByUsername(username)).thenReturn(Optional.empty());
        //Act
        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                serviceImpl.loadUserByUsername(username));
        //Assert
        String error = "User not found with username: " + username;
        assertEquals(error, thrown.getMessage());
    }

    @Test
    void loadUserByEmail_Fail_userNotFound() {
        //Arrange
        String email = RandomString.make()+"@email.com";
        when(UserRepository.findByEmail(email)).thenReturn(Optional.empty());
        //Act
        Exception thrown = Assertions.assertThrows(Exception.class, () ->
                serviceImpl.loadUserByEmail(email));
        //Assert
        String error = "User not found with email: " + email;
        assertEquals(error, thrown.getMessage());
    }

}