package project.musicwebsite.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("0")
public class Admin extends User {

}
