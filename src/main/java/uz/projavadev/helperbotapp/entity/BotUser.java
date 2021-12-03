package uz.projavadev.helperbotapp.entity;

import lombok.Data;
import uz.projavadev.helperbotapp.enums.State;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "users")
@Data
public class BotUser extends BaseEntity {

    private Integer tgId;
    private String firstName;
    private String lastName;
    private Boolean isBot;
    private String userName;
    private String languageCode;
    @Enumerated(EnumType.STRING)
    private State state;
    private Integer step;
    private Long advId;
}
