package demo.security.jwt;

import demo.domain.entities.Role;
import demo.domain.entities.User;
import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;
import demo.domain.valueobjects.UserId;
import demo.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
class AuthTokenFilterTest {

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthTokenFilter authTokenFilter;

    @MockBean
    private HttpServletRequest request;
    @MockBean
    private HttpServletResponse response;
    @MockBean
    private FilterChain filterChain;
    @MockBean
    private UserDetails userDetails;
    @MockBean
    private User user;


    @Test
    void doFilterInternal_nullJWT() throws ServletException, IOException {
        //Arrange
        //Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        //Assert
        assertNotNull(authTokenFilter);
    }

    @Test
    void doFilterInternal_falseJWT() throws ServletException, IOException {
        //Arrange
        String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmMxMjMiLCJpc3MiOiJ3d3cuYWJjLmNvbSIsImlhdCI6MTU5NDY0MTUzNywiZXhwIjoxNTk0NjQxODM3fQ.GHzVaQW_tvqo8HlDmoXzZ8WIYGcLHciLOSMFxsZUOsY";
        when(request.getHeader("Authorization")).thenReturn("Bearer "+token);
        //Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        //Assert
        assertNotNull(authTokenFilter);
    }

    @Test
    void doFilterInternal_exception() throws ServletException, IOException {
        //Arrange
        String username = "PersonUsername";
        when(user.getUsername()).thenReturn(username);

        String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmMxMjMiLCJpc3MiOiJ3d3cuYWJjLmNvbSIsImlhdCI6MTU5NDY0MTUzNywiZXhwIjoxNTk0NjQxODM3fQ.GHzVaQW_tvqo8HlDmoXzZ8WIYGcLHciLOSMFxsZUOsY";
        when(request.getHeader("Authorization")).thenReturn("Bearer "+token);

        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        //Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        //Assert
        assertNotNull(authTokenFilter);
    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
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

        //Mocks
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmMxMjMiLCJpc3MiOiJ3d3cuYWJjLmNvbSIsImlhdCI6MTU5NDY0MTUzNywiZXhwIjoxNTk0NjQxODM3fQ.GHzVaQW_tvqo8HlDmoXzZ8WIYGcLHciLOSMFxsZUOsY";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(jwtUtils.generateJwtToken(authentication)).thenReturn(token);
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        //Act
        authTokenFilter.doFilterInternal(request, response, filterChain);
        //Assert
        assertNotNull(authTokenFilter);
        verify(jwtUtils, times(1)).validateJwtToken(token);
        verify(jwtUtils, times(1)).getUserNameFromJwtToken(token);
    }

}