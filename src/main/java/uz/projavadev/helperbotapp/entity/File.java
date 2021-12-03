package uz.projavadev.helperbotapp.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class File extends BaseEntity {

    private String fileId;
    private Integer size;
    private String type;
    private String url;

}
