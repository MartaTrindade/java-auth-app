package demo.DTO;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href="https://bezkoder.com/spring-boot-jwt-authentication/">bezkoder</a>
 */

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class SignupRequestDTO extends RepresentationModel<UserDTO> {
    @Getter
    long id;
    @Getter
    String username;
    @Getter
    String firstName;
    @Getter
    String lastName;
    @Getter
    String email;
    @Getter
    String password;
}
