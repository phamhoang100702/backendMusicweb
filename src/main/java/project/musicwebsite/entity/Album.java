package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="Albumtbl")
@NoArgsConstructor
@Data
public class Album extends AbstractModel{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy@HH:mm:ss.SSSZ")
    private Date publish;
    private String thumbnail;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "singerId",nullable = true)
//    @JsonIgnore
   private Singer singer;

    @OneToMany(
            mappedBy = "album",
            fetch = FetchType.LAZY
    )
    private List<Song> songs = new LinkedList<>();

    public Album(String createdBy, String modifiedBy, String name, Date publish, String thumbnail) {
        super(createdBy, modifiedBy);
        this.name = name;
        this.publish = publish;
        this.thumbnail = thumbnail;
    }

    public Album(String createdBy, String modifiedBy, String name, String thumbnail) {
        super(createdBy, modifiedBy);
        this.name = name;
        this.publish = new Date();
        this.thumbnail = thumbnail;
    }

    public Album( String name, String thumbnail,Date date) {
        super();
        this.name = name;
        this.publish = date;
        this.thumbnail = thumbnail;
    }

    public Album(String name,String thumbnail){
        super();
        this.name = name;
        this.thumbnail = thumbnail;
        this.publish = new Date();
    }


}
