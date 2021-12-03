package uz.projavadev.helperbotapp.service;

import org.telegram.telegrambots.meta.api.objects.User;
import uz.projavadev.helperbotapp.entity.BotUser;

public interface UserService {

    BotUser save(User user);

    BotUser getOne(Integer tgId);

    BotUser update(Integer tgId, BotUser user);

}
