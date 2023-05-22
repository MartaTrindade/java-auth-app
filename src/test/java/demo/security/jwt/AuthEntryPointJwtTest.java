package demo.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class AuthEntryPointJwtTest {

    @MockBean
    HttpServletRequest request;
    @MockBean
    HttpServletResponse response;
    @MockBean
    AuthenticationException authException;
    @Autowired
    AuthEntryPointJwt entryPointJwt;

    @Test
    void commence() throws ServletException, IOException {
        //Arrange
        when(authException.getMessage()).thenReturn("Error: Unauthorized");
        //Act
        entryPointJwt.commence(request, response, authException);
        //Assert
        verify(authException, times(1)).getMessage();
        assertNotNull(entryPointJwt);
    }

}