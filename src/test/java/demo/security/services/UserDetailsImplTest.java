package demo.security.services;

import demo.domain.entities.Role;
import demo.domain.entities.User;
import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;
import demo.domain.valueobjects.UserId;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
class UserDetailsImplTest {

    @MockBean
    private User user;


    @Test
    void shouldBuildUserDetails() {
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
        String name = "Person";
        when(user.getFirstName()).thenReturn(name);
        String username = "PersonUsername";
        when(user.getUsername()).thenReturn(username);
        String email = RandomString.make() + "@email.com";
        when(user.getEmail()).thenReturn(email);
        //Act
        UserDetailsImpl result = UserDetailsImpl.build(user);
        //Assert
        assertNotNull(result.getAuthorities());
        assertEquals(userID, result.getId());
        assertEquals(email, result.getEmail());
        assertEquals(username, result.getUsername());
        assertTrue(result.isAccountNonExpired());
        assertTrue(result.isAccountNonLocked());
        assertTrue(result.isCredentialsNonExpired());
        assertTrue(result.isEnabled());
    }

    @Test
    void testEquals() {
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
        String name = "Person";
        when(user.getFirstName()).thenReturn(name);
        String email = RandomString.make() + "@email.com";
        when(user.getEmail()).thenReturn(email);
        //Act
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        UserDetailsImpl userDetails1 = UserDetailsImpl.build(user);
        boolean isEquals = userDetails.equals(userDetails1);
        //Assert
        assertEquals(userDetails, userDetails);
        assertEquals(userDetails, userDetails1);
        assertTrue(isEquals);
        assertNotEquals(null, userDetails);
        assertNotEquals(userDetails, role);
        assertEquals(userDetails.hashCode(), userDetails.hashCode());
    }

}