package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import project.musicwebsite.exception.BadRequestException;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private List<Category> categories = new LinkedList<>();

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


    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "song_singer",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id")
    )
    @JoinColumn(nullable = true)
    private List<Singer> singers = new LinkedList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "song",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Click> clicks = new ArrayList<>();


    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "songs"
    )
    @JsonIgnore
    private List<Playlist> playlist = new LinkedList<>();


    public void addSinger(Singer singer){
        if(singers.contains(singer)) throw new BadRequestException("This singer is existed");
        this.singers.add(singer);
        singer.getSongs().add(this);
    }

    public void removeSinger(Singer singer){
        if(!singers.contains(singer)) throw new BadRequestException("This singer is not existed");
        this.singers.remove(singer);
        singer.getSongs().remove(this);
    }

    public void addCategory(Category category){
        if(this.categories.contains(category)) throw  new BadRequestException("This category is exited");
        this.categories.add(category);
        category.getSongList().add(this);
    }

    public void removeCategory(Category category){
        if(!this.categories.contains(category)) throw  new BadRequestException("This category is not exited");

        this.categories.remove(category);
        category.getSongList().remove(this);
    }

}
