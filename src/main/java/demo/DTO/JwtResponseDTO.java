package demo.DTO;

import java.util.List;

import demo.domain.valueobjects.UserId;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="https://bezkoder.com/spring-boot-jwt-authentication/">bezkoder</a>
 */

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class JwtResponseDTO extends RepresentationModel<UserDTO> {
    @Getter
    @Setter
    String token;
    @Getter
    @Setter
    UserId id;
    @Getter
    @Setter
    String username;
    @Getter
    @Setter
    String email;
    @Getter
    @Setter
    List<String> roles;
}
