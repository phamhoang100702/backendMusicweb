package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("3")
public class Singer extends User {
    private String bio;

    private String socialMediaLink;

    @JoinColumn(nullable = true)
    private String nickName;
    @JoinColumn(columnDefinition = "boolean default true")
    private Boolean status;

   public Singer(){
       super();
       this.status = true;
   }

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "Followertbl",
            joinColumns = @JoinColumn(name = "singer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> followers = new LinkedList<>();

    @ManyToMany(
            mappedBy = "singers",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Song> songs = new LinkedList<>();

    public void addFollower(User user) {
        this.followers.add(user);
        user.getSingers().add(this);

    }

    public void removeFollow(User user) {
        this.followers.remove(user);
        user.getSingers().remove(this);
    }

    public boolean existFollower(User user) {
        return this.followers.contains(user);
    }

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "singer"
    )
    @JsonIgnore
    private List<Album> albums = new LinkedList<>();

}
