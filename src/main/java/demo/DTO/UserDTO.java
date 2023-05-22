package demo.DTO;

import demo.domain.valueobjects.UserId;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserDTO extends RepresentationModel<UserDTO> {
    @Getter
    UserId id;
    @Getter
    String firstName;
    @Getter
    String lastName;
}
