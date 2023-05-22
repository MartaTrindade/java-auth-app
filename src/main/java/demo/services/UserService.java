package demo.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import demo.domain.entities.User;
import demo.domain.valueobjects.UserId;
import demo.DTO.UserDTO;
import demo.DTO.assemblers.UserDomainDTOAssembler;
import demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDomainDTOAssembler userAssembler;

    public UserService() {
    }

    public UserDTO createAndSaveUser(long id, String firstName, String lastName, String email, String username, String password) {

        User newUser = new User(id, firstName, lastName, email, username, password);

        User newUserSaved = userRepository.save(newUser);

        UserDTO userDTO = userAssembler.toDTO(newUserSaved.getId(), newUserSaved.getFirstName(), newUserSaved.getLastName());

        return userDTO;
    }

    @Transactional
    public List<UserDTO> findAll() {

        List<User> setUsers = userRepository.findAll();

        List<UserDTO> setUserDTO = new ArrayList<UserDTO>();
        for (User user : setUsers) {
            UserDTO userDTO = userAssembler.toDTO(user.getId(), user.getFirstName(), user.getLastName());
            setUserDTO.add(userDTO);
        }

        return setUserDTO;
    }

    @Transactional
    public Optional<UserDTO> findById(UserId id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = userAssembler.toDTO(user.getId(), user.getFirstName(), user.getLastName());
            return Optional.of(userDTO);
        }

        return Optional.empty();
    }

    public List<UserDTO> findByLastName(String lastName) {

        List<User> setUsers = userRepository.findByLastName(lastName);

        List<UserDTO> setUserDTO = new ArrayList<UserDTO>();
        for (User user : setUsers) {
            UserDTO userDTO = userAssembler.toDTO(user.getId(), user.getFirstName(), user.getLastName());
            setUserDTO.add(userDTO);
        }

        return setUserDTO;
    }

}
