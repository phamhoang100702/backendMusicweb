package project.musicwebsite.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
public class Click extends AbstractModel{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Click click = (Click) o;
        return  click.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
