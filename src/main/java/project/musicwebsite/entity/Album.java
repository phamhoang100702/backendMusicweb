package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Entity
@Table(name="Albumtbl")
@Data
public class Album extends AbstractModel{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;

    private String thumbnail;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "singerId",nullable = true)
//    @JsonIgnore
   private Singer singer;

    @OneToMany(
            mappedBy = "album",
            fetch = FetchType.EAGER
    )
    private Set<Song> songs = new HashSet<>();

    public Album() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return album.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
