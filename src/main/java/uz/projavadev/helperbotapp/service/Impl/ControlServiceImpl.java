package uz.projavadev.helperbotapp.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.projavadev.helperbotapp.RestConstants;
import uz.projavadev.helperbotapp.entity.BotUser;
import uz.projavadev.helperbotapp.entity.Home;
import uz.projavadev.helperbotapp.enums.State;
import uz.projavadev.helperbotapp.feign.TelegramFeign;
import uz.projavadev.helperbotapp.repository.*;
import uz.projavadev.helperbotapp.service.ControlService;
import uz.projavadev.helperbotapp.service.HomeService;
import uz.projavadev.helperbotapp.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ControlServiceImpl implements ControlService {

    private final UserService userService;
    private final TelegramFeign telegramFeign;
    private final HomeService homeService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final HomeRepository homeRepository;
    private final PhoneRepository phoneRepository;
    private final OtherRepository otherRepository;
    private final CourtyardRepository courtyardRepository;

    public void updateHandle(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();

            String data = callbackQuery.getData();
            Integer inUsId = Math.toIntExact(callbackQuery.getFrom().getId());
            BotUser botUser = userRepository.findByTgId(inUsId);
            Home homeDb = homeRepository.findByUserId(botUser.getId());
            if (data.equals("ha")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Энди эълонни тасдиқланг:\n" +
                        "\n" +
                        "\uD83D\uDCE2 " + homeDb.getTitle() + "\n" +
                        "\n" +
                        "✅ " + homeDb.getDescription() + "\n" +
                        "\n" +
                        "\uD83D\uDCB0 " + homeDb.getCost() + "\n" +
                        "\n" +
                        "\uD83D\uDCDE " + homeDb.getPhoneNumber() + "\n" +
                        "\n" +
                        "\uD83D\uDCE1 " + homeDb.getAddress() + "\n" +
                        "\n" +
                        "\uD83D\uDC49 @buxoro_bozor");
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
                inlineKeyboardButton1.setText("Ҳа✅");
                inlineKeyboardButton1.setCallbackData("ha");
                inlineKeyboardButton2.setText("Йўқ❌");
                inlineKeyboardButton2.setCallbackData("yoq");
                inlineKeyboardButtons.add(inlineKeyboardButton1);
                inlineKeyboardButtons.add(inlineKeyboardButton2);
                inlineKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                sendMessage.setChatId(String.valueOf(754267606l));
                telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN, sendMessage);
            } else if (data.equals("yoq")) {
                deleteAdv(botUser.getState(), botUser.getAdvId());
                botUser.setState(State.G_HOME);
                botUser.setStep(0);
                botUser.setAdvId(0L);
                userRepository.save(botUser);
//                showButton(update, "Эълон бекор қилинди !");
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setSelective(true);
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                KeyboardRow keyboardRow1 = new KeyboardRow();
                KeyboardRow keyboardRow2 = new KeyboardRow();
                KeyboardRow keyboardRow3 = new KeyboardRow();
                KeyboardButton keyboardButton1 = new KeyboardButton();
                KeyboardButton keyboardButton2 = new KeyboardButton();
                KeyboardButton keyboardButton3 = new KeyboardButton();
                KeyboardButton keyboardButton4 = new KeyboardButton();
                KeyboardButton keyboardButton5 = new KeyboardButton();
                KeyboardButton keyboardButton6 = new KeyboardButton();
                keyboardButton1.setText("\uD83C\uDFE2 Уй");
                keyboardButton2.setText("\uD83C\uDFE0 Ҳовли");
                keyboardButton3.setText("\uD83D\uDE99 Машина");
                keyboardButton4.setText("\uD83D\uDCF1Телефон");
                keyboardButton5.setText("\uD83D\uDCE2 Бошка эълон");
                keyboardButton6.setText("Админ билан боғланиш");
                keyboardRow1.add(keyboardButton1);
                keyboardRow1.add(keyboardButton2);
                keyboardRow2.add(keyboardButton3);
                keyboardRow2.add(keyboardButton4);
                keyboardRow3.add(keyboardButton5);
                keyboardRow3.add(keyboardButton6);
                keyboardRowList.add(keyboardRow1);
                keyboardRowList.add(keyboardRow2);
                keyboardRowList.add(keyboardRow3);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setChatId(String.valueOf(callbackQuery.getFrom().getId()));
                sendMessage.setText("Эълон бекор қилинди !");
                telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN, sendMessage);
            }
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            User user = message.getFrom();
            Integer tgId = Math.toIntExact(user.getId());
            if (!userRepository.existsByTgId(tgId)) {
                userService.save(user);
            }
            BotUser botUser = userService.getOne(tgId);
            String state = userService.getOne(Math.toIntExact(user.getId())).getState().name();
            if (text != null) {
                if (!text.equals("/start") && !text.equals("❌ Бекор қилиш")) {
                    controlState(state, update, botUser);
                }

                switch (text) {
                    case "/start":
                        showButton(update, "Assalomu alaykum Jizzax shahar bo'ylab dostavka hizmatiga hush kelibsiz!");
                        break;
                    case "\uD83C\uDFE2 Уй":
                        controlStep(botUser, State.HOME);
                        cancel(update, "☝️ Сарлавҳа киритинг:\n" + " (масалан, 2 хонали уй сотилади)");
                        break;
                    case "\uD83C\uDFE0 Ҳовли":
                        controlStep(botUser, State.COURTYARD);
                        userRepository.save(botUser);
                        cancel(update, "\uD83C\uDFE0 Сарлавҳа киритинг:\n" + "(масалан, 2 хонали уй ҳовли, Ер, дача, котеж)");
                        break;
                    case "\uD83D\uDE99 Машина":
                        controlStep(botUser, State.CAR);
                        cancel(update, "\uD83D\uDE99 Машина номини ва йилини киритинг:\n" + "(намуна: Спарк 2017йил)");
                        break;
                    case "\uD83D\uDCF1Телефон":
                        controlStep(botUser, State.PHONE);
                        cancel(update, "\uD83D\uDCF1 Телефон номи:\n" + "(Samsung Galaxy J1)");
                        break;
                    case "\uD83D\uDCE2 Бошка эълон":
                        controlStep(botUser, State.OTHER);
                        cancel(update, "\uD83D\uDCE2 Сарлавҳа киритинг:\n" + "(Холоделник Samsung сотилади)");
                        break;
                    case "Админ билан боғланиш":
                        controlStep(botUser, State.ADMIN);
                        cancel(update, "\uD83D\uDC47 Админ билан богланиш учун кнопкани босинг");
                        break;
                    case "❌ Бекор қилиш":
                        if (botUser.getAdvId()!=0)
                        deleteAdv(botUser.getState(), botUser.getAdvId());
                        botUser.setState(State.G_HOME);
                        botUser.setStep(0);
                        botUser.setAdvId(0l);
                        userRepository.save(botUser);
                        showButton(update, "Эълон бекор қилинди !");
                        break;
                    case "✅Расмлар юкланди":
                        botUser.setStep(botUser.getStep() + 1);
                        userRepository.save(botUser);
                        cancel(update, "☝️ Телефон номерни киритинг:\n" +
                                "(+998912345678 шаклда)");
                        break;
                }
            } else {
                controlState(state, update, botUser);
            }
        }
    }

    public void deleteAdv(State state, Long advId) {
        switch (state) {
            case CAR:
                carRepository.deleteById(advId);
                break;
            case HOME:
                homeRepository.deleteById(advId);
                break;
            case OTHER:
                otherRepository.deleteById(advId);
                break;
            case COURTYARD:
                courtyardRepository.deleteById(advId);
                break;
            case PHONE:
                phoneRepository.deleteById(advId);
                break;

        }
    }

    public void controlStep(BotUser botUser, State state) {
        botUser.setState(state);
        botUser.setStep(1);
        userRepository.save(botUser);
    }

    public void controlState(String state, Update update, BotUser botUser) {
        switch (state) {
            case "HOME":
                homeService.save(update, botUser);
                break;
            case "COURTYARD":
                System.out.println("COURTYARD");
                break;
            case "CAR":
                System.out.println("CAR");
                break;
            case "PHONE":
                System.out.println("PHONE");
                break;
            case "OTHER":
                System.out.println("OTHER");
                break;
        }
    }

    //keyboard
    public void showButton(Update update, String message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        KeyboardButton keyboardButton2 = new KeyboardButton();
        KeyboardButton keyboardButton3 = new KeyboardButton();
        KeyboardButton keyboardButton4 = new KeyboardButton();
        KeyboardButton keyboardButton5 = new KeyboardButton();
        KeyboardButton keyboardButton6 = new KeyboardButton();
        keyboardButton1.setText("\uD83C\uDFE2 Уй");
        keyboardButton2.setText("\uD83C\uDFE0 Ҳовли");
        keyboardButton3.setText("\uD83D\uDE99 Машина");
        keyboardButton4.setText("\uD83D\uDCF1Телефон");
        keyboardButton5.setText("\uD83D\uDCE2 Бошка эълон");
        keyboardButton6.setText("Админ билан боғланиш");
        keyboardRow1.add(keyboardButton1);
        keyboardRow1.add(keyboardButton2);
        keyboardRow2.add(keyboardButton3);
        keyboardRow2.add(keyboardButton4);
        keyboardRow3.add(keyboardButton5);
        keyboardRow3.add(keyboardButton6);
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        keyboardRowList.add(keyboardRow3);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN, sendMessage);
    }

    public void cancel(Update update, String message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("❌ Бекор қилиш");
        keyboardRow.add(keyboardButton);
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setText(message);
        telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN, sendMessage);
    }
}
