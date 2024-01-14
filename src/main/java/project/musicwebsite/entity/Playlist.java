package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@Table(name = "Playlisttbl")
public class Playlist extends  AbstractModel{
    @Id

    @SequenceGenerator(
            name = "playlist_sequence",
            sequenceName = "playlist_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;

//    @Column(columnDefinition = "boolean default true")
    private Boolean status;

    public Playlist() {
        this.status = true;
    }

    private String role;

    @ManyToOne(
            fetch = FetchType.EAGER,
            targetEntity = User.class
    )
    @JoinColumn(name = "creatorId",nullable = true)
    private User creator;

    @OneToMany(
            mappedBy = "playlist",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = SongOfPlaylist.class

    )
    @JsonIgnore
    private Set<SongOfPlaylist> songOfPlaylist = new HashSet<>();





    public boolean isExisted(Song song){
        return this.songOfPlaylist.contains(song);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return this.id == playlist.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
