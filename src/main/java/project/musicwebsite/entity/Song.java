package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Songtbl")
public class Song extends AbstractModel {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "boolean default false")
    private Boolean status;
    @Column(nullable = false, unique = true)
    private String fileSong;
    @Column(nullable = false, unique = true)
    private String fileLyric;
    private String category;

    public boolean getStatus() {
        return this.status;
    }

    public Song(String createdBy, String modifiedBy, String name, boolean status,
                String fileSong, String fileLyric, Singer BSinger) {
        super(createdBy, modifiedBy);
        this.name = name;
        this.status = status;
        this.fileSong = fileSong;
        this.fileLyric = fileLyric;
        this.singer = BSinger;
    }

    public Song(String name, boolean status, String fileSong, String fileLyric) {
        super();
        this.name = name;
        this.status = status;
        this.fileSong = fileSong;
        this.fileLyric = fileLyric;
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
    @JoinColumn(name = "singerId", nullable = true)

    private Singer singer;


    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "songs"
    )
    @JsonIgnore
    private List<Playlist> playlist = new LinkedList<>();




}
