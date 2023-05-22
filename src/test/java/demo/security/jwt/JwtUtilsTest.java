package demo.security.jwt;

import demo.domain.entities.Role;
import demo.domain.entities.User;
import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;
import demo.domain.valueobjects.UserId;
import demo.security.services.UserDetailsImpl;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
class JwtUtilsTest {

    @MockBean
    private User user;
    @Autowired
    JwtUtils jwtUtils;

    @Test
    void generateJwtToken() {
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
        String email = RandomString.make()+"@email.com";
        when(user.getEmail()).thenReturn(email);
        String password = "123456789";
        when(user.getPassword()).thenReturn(password);

        //Mocks
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        //Act
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        boolean validateToken = jwtUtils.validateJwtToken(jwtToken);

        //Assert
        assertNotNull(jwtToken);
        assertEquals(userName, userName);
        assertTrue(validateToken);
    }

    @Test
    void validToken_createdOnTestSoItDoesNotExpire() {
        //Arrange
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = jwtUtils.generateJwtToken(authentication);
        //Act
        boolean result = jwtUtils.validateJwtToken(token);
        //Assert
        assertTrue(result);
    }

    @Test
    void invalidToken() {
        //Arrange
        String token = "eyJhbGciOiJIUzUxMiJ9";
        //Act
        boolean result = jwtUtils.validateJwtToken(token);
        //Assert
        assertFalse(result);
    }

    @Test
    void expiredToken() {
        //Arrange
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQZXJzb24iLCJpYXQiOjE2NTYzNjkzMDIsImV4cCI6MTY1NjQ1NTcwMn0.f3DJvTZzesuvrTjeJ_SW_dvC2b0le-i5XWDpjKoTutYA6FSaJA-f-ASJ-JwDJ8RgxRcRRtFF3ld6Y4AK_LMpbA";
        //Act
        boolean result = jwtUtils.validateJwtToken(token);
        //Assert
        assertFalse(result);
    }

    //unsupportedToken() {}

    @Test
    void emptyToken() {
        //Arrange
        String token = "";
        //Act
        boolean result = jwtUtils.validateJwtToken(token);
        //Assert
        assertFalse(result);
    }

}