package uz.projavadev.helperbotapp.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.projavadev.helperbotapp.RestConstants;
import uz.projavadev.helperbotapp.dto.GetFile;
import uz.projavadev.helperbotapp.dto.ResultTelegram;

@FeignClient(url = RestConstants.TELEGRAM_BASE_URL_WITHOUT_BOT, name = "Telegram")
public interface TelegramFeign {

    @PostMapping("{path}/sendMessage")
    ResultTelegram sendMessageToUser(@PathVariable String path, @RequestBody SendMessage sendMessage);

    @GetMapping("{path}/getFile")
    GetFile getFile(@PathVariable String path, @RequestParam String file_id);

}
