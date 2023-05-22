package demo.repositories.JPA;

import java.util.Optional;

import demo.datamodel.JPA.RoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.domain.valueobjects.ERole;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpa, Long> {

    Optional<RoleJpa> findByName(ERole name);

}
