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
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
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
