package project.musicwebsite.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Table(name = "Followertbl")
@Entity
@Data
@NoArgsConstructor
public class Follower {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = Singer.class
    )
    @JoinColumn(
            name = "singerId"
    )
    Singer singer;
    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = User.class
    )
    @JoinColumn(
            name = "userId"
    )
    User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follower follower = (Follower) o;
        return Objects.equals(id, follower.id) && Objects.equals(singer, follower.singer) && Objects.equals(user, follower.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, singer, user);
    }
}
