package org.example.service.telegram.V2.panel;

import lombok.SneakyThrows;
import org.example.config.ConfigBean;
import org.example.service.telegram.V2.batton.CreateUserButton;
import org.example.service.telegram.V2.interfaces.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserPanel implements Panel {

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private CreateUserButton button;

    @SneakyThrows
    public SendMessage inCommand(Update update) {

        if (update.getMessage().hasText() && configBean.commandList().contains(update.getMessage().getText())) {
            return button.start(update);
        }
        if (update.getMessage().hasContact()) {
            return null;
        }
        if (update.getMessage().hasLocation()) {
            return null;
        }
        return null;
    }
}
