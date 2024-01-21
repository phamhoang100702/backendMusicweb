package project.musicwebsite.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.exception.BadRequestException;

import java.util.*;


@Data
@Entity
@Table(name = "Usertbl")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role",
        discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class User extends AbstractModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            columnDefinition = "varchar(50) default 'User'"
    )
    private String name;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
//    @JsonIgnore
    private String password;

    @Column(insertable = false, updatable = false)

    private int role;


    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private List<Role> roles = new ArrayList<>();

    private String avatar;


    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user"
    )
    @JsonIgnore
    private Set<Follower> followers = new TreeSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "creator"
    )
    @JsonIgnore
    private Set<Playlist> createdPlaylist = new TreeSet<>();


    public User() {
        super();
    }

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = Click.class
    )
    @JsonIgnore
    private Set<Click> clicks = new HashSet<>();

    public User(String createdBy, String modifiedBy, String name, String email, String password) {
        super(createdBy, modifiedBy);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addRole(Role role){
        if(this.roles.contains(role)) throw  new BadRequestException("Role is existed");
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role){
        if(!this.roles.contains(role)) throw new BadRequestException("Role is not existed");
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
