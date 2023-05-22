package demo.datamodel.JPA;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import demo.domain.valueobjects.ERole;
import demo.domain.valueobjects.RoleId;

@Entity
@Table(name = "roles")
public class RoleJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Embedded
    private RoleId id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public RoleJpa() {
    }

    public RoleJpa(ERole name) {
        this.name = name;
    }

    public RoleJpa(RoleId id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public RoleId getId() {
        return id;
    }

    public void setId(RoleId id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
