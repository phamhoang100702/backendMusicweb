package project.musicwebsite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue("4")
public class Censor extends User {

    private Date birth;
    private String address;
    @NotBlank(message = "phone is mandatory")
    private String phone;
    @Column(columnDefinition = "boolean default false")
    private Boolean status;

    public Censor(String createdBy, String modifiedBy, String name,
                  String email, String password, Date birth, String address,
                  String phone, Boolean status) {
        super(createdBy, modifiedBy, name, email, password);
        this.birth = birth;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }
}
