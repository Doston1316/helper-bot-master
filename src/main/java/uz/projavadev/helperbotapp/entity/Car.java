package uz.projavadev.helperbotapp.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Car extends BaseEntity {

    private String name;
    private String year;
    private String mileage;
    private String description;
    private String fuel;
    private String cost;
    @OneToMany
    private List<File> files;
    private String phoneNumber;
    private String address;
    private Boolean confirm;
    @ManyToOne
    private BotUser user;

}
