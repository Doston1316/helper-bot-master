package uz.projavadev.helperbotapp.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.projavadev.helperbotapp.RestConstants;
import uz.projavadev.helperbotapp.dto.FileDto;
import uz.projavadev.helperbotapp.dto.GetFile;
import uz.projavadev.helperbotapp.entity.BotUser;
import uz.projavadev.helperbotapp.entity.File;
import uz.projavadev.helperbotapp.entity.Home;
import uz.projavadev.helperbotapp.feign.TelegramFeign;
import uz.projavadev.helperbotapp.repository.FileRepository;
import uz.projavadev.helperbotapp.repository.HomeRepository;
import uz.projavadev.helperbotapp.repository.UserRepository;
import uz.projavadev.helperbotapp.service.HomeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final TelegramFeign telegramFeign;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final FileRepository fileRepository;

    @Override
    public void save(Update update, BotUser user) {
        Integer step = user.getStep();
        Message message = update.getMessage();

        switch (step) {
            case 1:
                getAndSend(message, user, step, "111111");
                break;
            case 2:
                getAndSend(message, user, step, "222222");
                break;
            case 3:
                getAndSend(message, user, step, "3333333");
                break;
            case 4:
                if (message.getText() != null) {
                    if (!message.getText().equals("✅Расмлар юкланди")) {
                        getAndSend(message, user, step, "Илтимос расм жўнатинг ❗");
                    }
                } else {
                    getAndSend(message, user, step, "Расмни қабул қилдим✅,\n" +
                            "✅Расмлар юкланди тугмасини босинг !");
                }
                break;
            case 5:
                getAndSend(message, user, step, "☝️ Манзилни киритинг:\n" +
                        "(масалан, Бухоро Ш)");
                break;
            case 6:
                getAndSend(message, user, step, "⏳ Эълонингиз тайёрланмоқда...");
                break;
            case 7:
                getAndSend(message, user, step, "");
                break;
        }
    }

    public void getAndSend(Message message, BotUser user, Integer step, String reply) {
        Home home = null;
        if (step == 1) {
            Home newHome = new Home();
            newHome.setTitle(message.getText());
            newHome.setUser(user);
            user.setAdvId(homeRepository.save(newHome).getId());
            userRepository.save(user);
        } else {
            home = homeRepository.getById(user.getAdvId());
            List<File> files = new ArrayList<>();
            switch (step) {
                case 2:
                    home.setDescription(message.getText());
                    homeRepository.save(home);
                    break;
                case 3:
                    home.setCost(message.getText());
                    homeRepository.save(home);
                    break;
                case 4:
                    if (message.getText() == null) {
                        GetFile getFile = telegramFeign.getFile("bot" + RestConstants.BOT_TOKEN, message.getPhoto().get(1).getFileId());
                        FileDto fileDto = getFile.getResult();
                        File file = new File();
                        file.setFileId(fileDto.getFile_id());
                        file.setSize(fileDto.getFile_size());
                        file.setType(fileDto.getFile_path().substring(fileDto.getFile_path().length() - 3));
                        file.setUrl(RestConstants.TELEGRAM_BASE_URL_WITHOUT_BOT + "file/bot" + RestConstants.BOT_TOKEN + "/" + fileDto.getFile_path());
                        List<File> filesDb = homeRepository.getById(home.getId()).getFiles();
                        files.addAll(filesDb);
                        files.add(fileRepository.save(file));
                        home.setFiles(files);
                        homeRepository.save(home);
                    }
                    break;
                case 5:
                    home.setPhoneNumber(message.getText());
                    homeRepository.save(home);
                    break;
                case 6:
                    home.setAddress(message.getText());
                    homeRepository.save(home);
                    break;
                case 7:
//                    home.setAddress(message.getText());
//                    homeRepository.save(home);
                    break;
            }
        }

//         homeRepository.save(home);
        if (step != 4) {
            user.setStep(++step);
        }
        userRepository.save(user);
        if (step == 4) {
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            KeyboardButton keyboardButton2 = new KeyboardButton();
            keyboardButton.setText("❌ Бекор қилиш");
            keyboardButton2.setText("✅Расмлар юкланди");
            keyboardRow.add(keyboardButton);
            keyboardRow.add(keyboardButton2);
            keyboardRowList.add(keyboardRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            SendMessage sendMessage = new SendMessage(message.getChatId().toString(), reply);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN, sendMessage);
        } else if (message.getText().equals("⏳ Эълонингиз тайёрланмоқда...")) {
            Home homeDb = homeRepository.getById(home.getId());
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
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN, sendMessage);
        } else {
            telegramFeign.sendMessageToUser("bot" + RestConstants.BOT_TOKEN,
                    new SendMessage(message.getChatId().toString(), reply));
        }
    }
}
