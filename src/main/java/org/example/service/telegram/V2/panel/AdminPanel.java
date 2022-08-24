package org.example.service.telegram.V2.panel;

import org.example.config.ConfigBean;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.service.telegram.V2.interfaces.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class AdminPanel implements Panel {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ConfigBean configBean;


    public SendMessage inCommand(Update update) {
        if (update.getMessage().hasText() && configBean.commandListAdmin().contains(update.getMessage().getText())) {
            SendMessage sendMessage = configBean.mapAdmin().get(update.getMessage().getText()).apply(update);
            sendMessage.disableNotification(); // отключили звук сообщения бота
            return sendMessage;
        }
        return null;
    }


}