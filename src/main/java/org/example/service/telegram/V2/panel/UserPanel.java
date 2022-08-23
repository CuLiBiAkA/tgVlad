package org.example.service.telegram.V2.panel;

import lombok.SneakyThrows;
import org.example.config.Command;
import org.example.config.ConfigBean;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.telegram.V2.batton.Steep;
import org.example.service.telegram.V2.interfaces.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserPanel implements Panel {

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private UserService userService;

    @Autowired
    Steep steep;

    @Autowired
    Command command;


    @SneakyThrows
    public SendMessage inCommand(Update update) {
        if (update.getMessage().hasText() &&
                configBean.commandList().contains(update.getMessage().getText()) &&
                configBean.map().containsKey(update.getMessage().getText())) {
            steep.listStepAdd(update);
            SendMessage sendMessage = configBean.map().get(update.getMessage().getText()).apply(update);
            sendMessage.disableNotification(); // отключили звук сообщения бота
            return sendMessage;
        }
        if (update.getMessage().hasContact()) {
            return configBean.map().get(command.getCONTACT_SEND()).apply(update);
        }

        if (update.getMessage().hasLocation()) {
            return null;
        }

        if (update.getMessage().hasText() && Pattern.matches("^Заявка №\\d*\\n+.*",update.getMessage().getText())) {
            return configBean.map().get("Заявка №*").apply(update);
        }

        if (!configBean.listStep().isEmpty() && configBean.listStep().get(update.getMessage().getChatId()).getLast().equals(command.getRefactorAddress()) &&
                (update.getMessage().hasText())) {
            User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());
            if (userByChatId != null) {
                userByChatId.setAddres(update.getMessage().getText());
                userService.saveUser(userByChatId);
                return configBean.map().get(command.getBACK()).apply(update);
            } else {
                userService.saveUser(new User(null,
                        null,
                        null,
                        update.getMessage().getText(),
                        null,
                        update.getMessage().getChatId(),
                        false,
                        null));
                return configBean.map().get(command.getBACK()).apply(update);
            }
        }

        if (!configBean.listStep().isEmpty() && configBean.listStep().get(update.getMessage().getChatId()).getLast().equals(command.getRefactorName()) &&
                (update.getMessage().hasText())) {
            User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());
            if (userByChatId != null) {
                userByChatId.setFistName(update.getMessage().getText());
                userService.saveUser(userByChatId);
                return configBean.map().get(command.getBACK()).apply(update);
            } else {
                userService.saveUser(new User(null,
                        update.getMessage().getText(),
                        null,
                        null,
                        null,
                        update.getMessage().getChatId(),
                        false,
                        null));
                return configBean.map().get(command.getBACK()).apply(update);
            }
        }

        if (!configBean.listStep().isEmpty() && configBean.listStep().get(update.getMessage().getChatId()).getLast().equals(command.getRefactorPhone()) &&
                (update.getMessage().hasText())) {
            User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());
            if (userByChatId != null) {
                userByChatId.setNumber(update.getMessage().getText());
                userService.saveUser(userByChatId);
                return configBean.map().get(command.getBACK()).apply(update);
            } else {
                userService.saveUser(new User(null,
                        null,
                        null,
                        null,
                        update.getMessage().getText(),
                        update.getMessage().getChatId(),
                        false,
                        null));
                return configBean.map().get(command.getBACK()).apply(update);
            }
        }
        return null;
    }
}
