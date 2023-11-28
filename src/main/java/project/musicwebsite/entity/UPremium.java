package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class UPremium extends User {



//    @Column(nullable = false)
    private Date startTime;
//    @Column(nullable = false)
    private Date endTime;
    private String hobby;
    private Date birth;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "uPremiums"
    )
    @JsonIgnore
    private List<PremiumPackage> packages = new LinkedList<>();
}
