package project.musicwebsite.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Click {
    @SequenceGenerator(
        name="click_generator",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "click_generator"
    )
    private Long id;
    private Date timeClick;

    @ManyToOne(
            fetch =  FetchType.LAZY
    )
    @JoinColumn(
            name = "userId",
            nullable = false
    )
    private User user;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "songId"
    )
    private Song song;

}
