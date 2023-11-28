package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
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
            strategy = GenerationType.SEQUENCE,
            generator = "playlist_sequence"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
    @JoinColumn(name = "creatorId",nullable = false)
    private User creator;

    private String name;

    @Column(columnDefinition = "boolean default true")
    private Boolean status;


    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name="SongPlaylisttbl",
            joinColumns = @JoinColumn(name="playlist_id"),
            inverseJoinColumns = @JoinColumn(name="song_id")
    )
    @JsonIgnore
    private List<Song> songs = new LinkedList<>();

    public void addSong(Song song){
        this.songs.add(song);
        song.getPlaylist().add(this);
    }

    public void removeSong(Song song){
        this.songs.remove(song);
        song.getPlaylist().remove(this);
    }

    public boolean isExisted(Song song){
        return this.songs.contains(song);
    }

    public Playlist(String createdBy, String modifiedBy, User creator, String name, Boolean status, List<Song> songs) {
        super(createdBy, modifiedBy);
        this.creator = creator;
        this.name = name;
        this.status = status;
        this.songs = songs;
    }
}
