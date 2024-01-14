package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import project.musicwebsite.exception.BadRequestException;

import java.util.*;

@Entity
@Data
@Table(name = "Songtbl")
// Song SInger ver dang n-n
public class Song extends AbstractModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    private Integer status;
    @Column(nullable = false, unique = false)
    private String fileSound;
    @Column(nullable = true, unique = false)
    private String fileLyric;


    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "song_category",
            joinColumns = @JoinColumn(name = "song_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="category_id",referencedColumnName = "id")
    )
    @JoinColumn(nullable = true)
    private Set<Category> categories = new HashSet<>();


    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "SingersOfSong",
            joinColumns = @JoinColumn(name = "song_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id",referencedColumnName = "id")
    )
    @JoinColumn(nullable = true)
    private Set<Singer> singers = new HashSet<>();

    @Column(nullable = true)
    private String avatar;

    public Song() {
        super();
        this.status = 0;
    }

    public Integer getStatus() {
        return this.status;
    }




    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "albumId",
            nullable = true
    )
    @JsonIgnore
    private Album album;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "creatorId", nullable = true)
//    @JsonIgnore
    private User creator;




    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "song"
    )
    @JsonIgnore
    private Set<Click> clicks = new HashSet<>();


    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "song"
    )
    @JsonIgnore
    private Set<SongOfPlaylist> songOfPlaylists = new HashSet<>();


//    @ManyToMany(
//            fetch = FetchType.LAZY,
////            cascade = CascadeType.,
//            mappedBy = "songOfPlaylist"
//    )
//    @JsonIgnore
//    private Set<Playlist> playlist = new HashSet<>();


    public void addCategory(Category category){
        if(this.categories.contains(category)) return;
        this.categories.add(category);
        category.getSongList().add(this);
    }

    public void removeCategory(Category category){
        System.out.println("check 1  " + category.getId());
        if(!this.categories.contains(category)) return;

        this.categories.remove(category);
        category.getSongList().remove(this);
    }

    public void addSingerToSong(Singer singer){
        if(this.singers.contains(singer)) return;;
        this.singers.add(singer);
        singer.getSongsOfSinger().add(this);
    }

    public void removeSingerFromSong(Singer singer){
        if(!this.singers.contains(singer)) return;;
        this.singers.remove(singer);
        singer.getSongsOfSinger().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
        Song song = (Song) o;
        return this.id == song.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
