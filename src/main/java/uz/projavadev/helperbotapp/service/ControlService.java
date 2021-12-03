package uz.projavadev.helperbotapp.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ControlService {

    void updateHandle(Update update);
}
