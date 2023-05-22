package demo.datamodel.JPA;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import demo.domain.valueobjects.UserId;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserJpa {
    @Id
    @Embedded
    @Getter
    private UserId id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleJpa> roles = new HashSet<>();

    public UserJpa(long id, String firstName, String lastName, String email, String username, String password) {

        this.id = new UserId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public UserJpa(UserId id, String firstName, String lastName, String email, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return "User {" + "id='" + id.toString() + '\'' + '}';
    }

    public Set<RoleJpa> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleJpa> roles) {
        this.roles = roles;
    }

}
