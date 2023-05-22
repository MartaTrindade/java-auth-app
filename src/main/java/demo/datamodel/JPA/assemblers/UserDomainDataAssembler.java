package demo.datamodel.JPA.assemblers;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import demo.datamodel.JPA.UserJpa;
import demo.datamodel.JPA.RoleJpa;
import demo.domain.entities.User;
import demo.domain.entities.Role;

@Service
public class UserDomainDataAssembler {

    public UserJpa toData(User user) {
        UserJpa userJpa = new UserJpa(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), user.getPassword());

        Set<Role> rolesDTO = user.getRoles();
        Set<RoleJpa> rolesJpa = new HashSet<RoleJpa>();

        for (Role role : rolesDTO) {
            RoleJpa roleJpa = new RoleJpa(role.getId(), role.getName());
            rolesJpa.add(roleJpa);
        }
        userJpa.setRoles(rolesJpa);
        return userJpa;
    }

    public User toDomain(UserJpa userJpa) {
        User user = new User(userJpa.getId(), userJpa.getFirstName(), userJpa.getLastName(), userJpa.getEmail(), userJpa.getUsername(), userJpa.getPassword());

        Set<Role> roles = new HashSet<Role>();

        for (RoleJpa roleJpa : userJpa.getRoles()) {
            Role role = new Role(roleJpa.getId(), roleJpa.getName());
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }

}
