package demo.repositories.JPA;

import java.util.Optional;
import java.util.List;

import demo.datamodel.JPA.UserJpa;
import demo.domain.valueobjects.UserId;

import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<UserJpa, UserId> {

    List<UserJpa> findAll();

    Optional<UserJpa> findById(UserId id);

    Optional<UserJpa> findByUsername(String username);

    Optional<UserJpa> findByEmail(String email);

    List<UserJpa> findByLastName(String lastName);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
