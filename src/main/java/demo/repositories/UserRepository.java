package demo.repositories;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import java.util.List;

import demo.datamodel.JPA.UserJpa;
import demo.datamodel.JPA.assemblers.UserDomainDataAssembler;
import demo.domain.entities.User;
import demo.domain.valueobjects.UserId;
import demo.repositories.JPA.UserJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    UserDomainDataAssembler userAssembler;

    public User save(User user) {
        UserJpa userJpa = userAssembler.toData(user);

        UserJpa savedUserJpa = userJpaRepository.save(userJpa);

        return userAssembler.toDomain(savedUserJpa);
    }

    @Transactional
    public Optional<User> findById(UserId id) {
        Optional<UserJpa> optionalUserJpa = userJpaRepository.findById(id);

        if (optionalUserJpa.isPresent()) {
            UserJpa userJpa = optionalUserJpa.get();

            User user = userAssembler.toDomain(userJpa);
            return Optional.of(user);
        } else
            return Optional.empty();
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        Optional<UserJpa> optionalUserJpa = userJpaRepository.findByUsername(username);

        if (optionalUserJpa.isPresent()) {
            UserJpa userJpa = optionalUserJpa.get();

            User user = userAssembler.toDomain(userJpa);
            return Optional.of(user);
        } else
            return Optional.empty();
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        Optional<UserJpa> optionalUserJpa = userJpaRepository.findByEmail(email);

        if (optionalUserJpa.isPresent()) {
            UserJpa userJpa = optionalUserJpa.get();

            User user = userAssembler.toDomain(userJpa);
            return Optional.of(user);
        } else
            return Optional.empty();
    }

    @Transactional
    public List<User> findByLastName(String lastName) {
        List<UserJpa> setUserJpa = userJpaRepository.findByLastName(lastName);

        List<User> setUsers = new ArrayList<User>();
        for (UserJpa userJpa : setUserJpa) {
            User user = userAssembler.toDomain(userJpa);
            setUsers.add(user);
        }

        return setUsers;
    }

    @Transactional
    public List<User> findAll() {
        List<UserJpa> setUserJpa = userJpaRepository.findAll();

        List<User> setUsers = new ArrayList<User>();
        for (UserJpa userJpa : setUserJpa) {
            User user = userAssembler.toDomain(userJpa);
            setUsers.add(user);
        }

        return setUsers;
    }

    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

}
