package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.musicwebsite.exception.NotFoundException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class UPremium extends User {



//    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date startTime;
//    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date endTime;
    private String hobby;
    private Date birth;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name="Registertbl",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="package_id")
    )
    @JsonIgnore
    private List<PremiumPackage> packages = new LinkedList<>();

    public void addPackage(PremiumPackage uPremium) {
        this.packages.add(uPremium);
        uPremium.getUPremiums().add(this);
    }

    public void removePackage(PremiumPackage uPremium){
        if(isExisted(uPremium)) throw new NotFoundException("This package is not existed");
        this.packages.remove(uPremium);
        uPremium.getUPremiums().remove(this);
    }

    public boolean isExisted(PremiumPackage uPremium){
        return this.getPackages().contains(uPremium);
    }
}
