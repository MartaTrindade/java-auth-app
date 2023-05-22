package demo.domain.entities;

import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;

import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href="https://bezkoder.com/spring-boot-jwt-authentication/">bezkoder</a>
 */

@ToString
public class Role {
    @Getter
    private RoleId id;
    @Getter
    private ERole name;


    /**
     * Constructor
     *
     * @param roleId long
     * @param name   role Enum name
     */
    public Role(RoleId roleId, ERole name) {
        this.id = roleId;
        this.name = name;
    }

}
