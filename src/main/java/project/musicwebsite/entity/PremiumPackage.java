package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "PremiumPackagetbl")

public class PremiumPackage extends  AbstractModel{
    @Id

    @SequenceGenerator(
            name = "package_sequence",
            sequenceName = "package_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "package_sequence"
    )
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private int duration;
    private double cost;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name="Registertbl",
            joinColumns = @JoinColumn(name="package_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    @JsonIgnore
    private List<UPremium> uPremiums = new LinkedList<>();

    public void addUser(UPremium uPremium) {
        this.uPremiums.add(uPremium);
        uPremium.getPackages().add(this);
    }

    public void removeUser(UPremium uPremium){
        this.uPremiums.remove(uPremium);
        uPremium.getPackages().remove(this);
    }

    public boolean isExisted(UPremium uPremium){
        return this.getUPremiums().contains(uPremium);
    }



}
