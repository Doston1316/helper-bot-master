package uz.projavadev.helperbotapp.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Other extends BaseEntity {

    private String title;
    private String description;
    private String cost;
    @OneToMany
    private List<File> files;
    private String phoneNumber;
    private String address;
    private Boolean confirm;
    @ManyToOne
    private BotUser user;

}
