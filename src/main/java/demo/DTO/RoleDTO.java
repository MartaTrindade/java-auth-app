package demo.DTO;

import demo.domain.valueobjects.RoleId;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class RoleDTO extends RepresentationModel<RoleDTO> {
    @Getter
    RoleId id;
    @Getter
    String name;
}
