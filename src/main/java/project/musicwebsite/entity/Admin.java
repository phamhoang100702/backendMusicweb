package project.musicwebsite.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@DiscriminatorValue("0")
@NoArgsConstructor
public class Admin extends User implements Serializable {

}
