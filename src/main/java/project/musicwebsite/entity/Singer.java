package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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

    @OneToMany(
            mappedBy = "singer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JsonIgnore
    private Set<Follower> followers = new HashSet<>();


    @OneToMany(
            mappedBy = "creator",
            fetch = FetchType.LAZY,
            targetEntity = Song.class,
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private Set<Song> songOfCreator = new HashSet<>();

    @ManyToMany(
            mappedBy = "singers",
            fetch = FetchType.LAZY,
            targetEntity = Song.class
    )
    @JsonIgnore
    private Set<Song> songsOfSinger = new HashSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "singer",
            targetEntity = Album.class
    )
    @JsonIgnore
    private Set<Album> albums = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
        Singer singer = (Singer) o;
        return singer.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
