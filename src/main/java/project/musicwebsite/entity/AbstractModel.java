package project.musicwebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@MappedSuperclass

public class AbstractModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy@HH:mm:ss.SSSZ")
    private Date createdDate;

    public AbstractModel(String createdBy, String modifiedBy) {
        this.createdDate = new Date();
        this.createdBy = createdBy;
        this.modifiedDate = new Date();
        this.modifiedBy = modifiedBy;
    }

    public AbstractModel() {
        this.createdDate = new Date();
        this.modifiedDate = new Date();
    }

    @JsonIgnore
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy@HH:mm:ss.SSSZ")
    private Date modifiedDate;
    @JsonIgnore
    private String modifiedBy;
}
