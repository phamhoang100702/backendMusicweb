package project.musicwebsite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@Table(name="Albumtbl")
@NoArgsConstructor
public class Album extends AbstractModel{

    @Id
    @SequenceGenerator(
            name = "album_sequence",
            sequenceName = "album_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "album_sequence"
    )
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Date publish;
    private String thumbnail;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(name = "singerId",nullable = true)
   private Singer singer;

    public Album(String createdBy, String modifiedBy, String name, Date publish, String thumbnail, Singer singer) {
        super(createdBy, modifiedBy);
        this.name = name;
        this.publish = publish;
        this.thumbnail = thumbnail;
        this.singer = singer;
    }
}
