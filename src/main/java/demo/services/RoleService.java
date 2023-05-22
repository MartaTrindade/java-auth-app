package demo.services;

import demo.domain.entities.Role;
import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;
import demo.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleService() {
    }

    public Role createAndSaveRole(RoleId id, ERole name) {
        Role role = new Role(id, name);

        return roleRepository.save(role);
    }

}
