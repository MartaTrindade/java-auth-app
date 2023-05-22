package demo.domain.entities;

import java.util.HashSet;
import java.util.Set;

import demo.domain.valueobjects.UserId;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class User {
    @Getter
    private final UserId id;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String username;
    @Getter
    private String password;
    @Getter
    private String email;
    @Setter
    @Getter
    private Set<Role> roles = new HashSet<Role>();


    /**
     * Constructor
     *
     * @param id        long
     * @param firstName user first name
     * @param lastName  user surname
     * @param email     user email, unique value
     * @param username  username, unique value
     * @param password  user password whit more than 8 digits with lowercase, uppercase, number, optional special char
     */
    public User(long id, String firstName, String lastName, String email, String username, String password) {
        this.id = new UserId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(UserId id, String firstName, String lastName, String email, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
