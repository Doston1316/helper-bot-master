package uz.projavadev.helperbotapp.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.projavadev.helperbotapp.entity.BotUser;

public interface HomeService {

    void save(Update update, BotUser user);
}
