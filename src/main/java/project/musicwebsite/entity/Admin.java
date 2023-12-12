package project.musicwebsite.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("0")
@NoArgsConstructor
public class Admin extends User {

}
