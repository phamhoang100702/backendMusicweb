package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Category  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "categories")
    @JsonIgnore
    private Set<Song> songList = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        System.out.println("Di Qua day ");

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return this.id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
