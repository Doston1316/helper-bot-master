package uz.projavadev.helperbotapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.projavadev.helperbotapp.service.Impl.ControlServiceImpl;

@RestController
@RequestMapping("api/v1/helper")
@RequiredArgsConstructor
public class MainController {

    private final ControlServiceImpl controlServiceImpl;

    @PostMapping()
    public void post(@RequestBody Update update) {
        controlServiceImpl.updateHandle(update);
    }
}
