package project.musicwebsite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@Entity
@DiscriminatorValue("4")
public class Censor extends User {

    private String address;
//    @NotBlank(message = "phone is mandatory")
    private String phone;
    @Column(columnDefinition = "boolean default true")
    private Boolean status;

    public Censor() {
        super();
        this.status = true;
    }

    public Censor(String createdBy, String modifiedBy, String name,
                  String email, String password, Date birth, String address,
                  String phone, Boolean status) {
        super(createdBy, modifiedBy, name, email, password);
        this.address = address;
        this.phone = phone;
        this.status = status;
    }
    public Censor(String name,String email, String password){
        super(name,email,password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Censor censor = (Censor) o;
        return censor.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
