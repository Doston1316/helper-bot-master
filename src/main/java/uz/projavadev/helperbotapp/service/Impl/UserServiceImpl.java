package uz.projavadev.helperbotapp.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.projavadev.helperbotapp.entity.BotUser;
import uz.projavadev.helperbotapp.enums.State;
import uz.projavadev.helperbotapp.repository.UserRepository;
import uz.projavadev.helperbotapp.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public BotUser save(User user) {
        if (!repository.existsByTgId(Math.toIntExact(user.getId()))) {
            BotUser botUser = new BotUser();
            botUser.setTgId(Math.toIntExact(user.getId()));
            botUser.setFirstName(user.getFirstName());
            botUser.setLastName(user.getLastName());
            botUser.setIsBot(user.getIsBot());
            botUser.setUserName(user.getUserName());
            botUser.setLanguageCode(user.getLanguageCode());
            botUser.setState(State.G_HOME);
            botUser.setStep(0);
            return repository.save(botUser);
        }
        return repository.findByTgId(Math.toIntExact(user.getId()));
    }

    @Override
    public BotUser getOne(Integer tgId) {
        return repository.findByTgId(tgId);
    }

    @Override
    public BotUser update(Integer tgId, BotUser user) {
        return null;
    }
}
