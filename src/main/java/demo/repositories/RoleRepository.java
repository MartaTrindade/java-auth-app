package demo.repositories;

import java.util.Optional;

import demo.datamodel.JPA.RoleJpa;
import demo.datamodel.JPA.assemblers.RoleDomainDataAssembler;
import demo.domain.entities.Role;
import demo.domain.valueobjects.ERole;
import demo.repositories.JPA.RoleJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RoleRepository {
    @Autowired
    RoleJpaRepository roleJpaRepository;

    @Autowired
    RoleDomainDataAssembler roleAssembler;

    public Role save(Role role) {
        RoleJpa roleJpa = roleAssembler.toData(role);

        RoleJpa savedRoleJpa = roleJpaRepository.save(roleJpa);

        return roleAssembler.toDomain(savedRoleJpa);
    }

    public Optional<Role> findByName(ERole name) {
        Optional<RoleJpa> opRole = roleJpaRepository.findByName(name);

        if (opRole.isPresent()) {
            Role role = roleAssembler.toDomain(opRole.get());
            return Optional.of(role);
        } else
            return Optional.empty();
    }
}
