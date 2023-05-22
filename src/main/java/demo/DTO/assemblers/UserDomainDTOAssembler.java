package demo.DTO.assemblers;

import demo.DTO.UserDTO;
import demo.domain.valueobjects.UserId;

import org.springframework.stereotype.Service;

@Service
public class UserDomainDTOAssembler {

    private UserDomainDTOAssembler() {
    }

    public UserDTO toDTO(UserId id, String firstName, String lastName) {
        return new UserDTO(id, firstName, lastName);
    }

}
