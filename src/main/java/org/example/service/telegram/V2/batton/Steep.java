package org.example.service.telegram.V2.batton;

import lombok.SneakyThrows;
import org.example.config.Command;
import org.example.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayDeque;


@Component
public class Steep {

    @Autowired
    ConfigBean configBean;

    @Autowired
    Command command;


    public SendMessage backMethod(Update update) {
        if (configBean.listStep().containsKey(update.getMessage().getChatId()) &&
                !configBean.listStep().get(update.getMessage().getChatId()).isEmpty() &&
                !configBean.listStep().get(update.getMessage().getChatId()).equals(command.getSTART()) &&
                !configBean.listStep().get(update.getMessage().getChatId()).equals(command.getMENU())) {
            try {
                configBean.listStep().get(update.getMessage().getChatId()).removeLast(); // удаляем метод назад
                configBean.listStep().get(update.getMessage().getChatId()).removeLast(); // удаляем метод последний
            } catch (Exception ignored) {
                return configBean.map().get(command.getMENU()).apply(update);
            }
            try {
                var lastCommandLocal = configBean.listStep().get(update.getMessage().getChatId()).getLast(); // пробуем выполнить метод
                return configBean.map().get(lastCommandLocal).apply(update);
            } catch (Exception ignored) {
                return configBean.map().get(command.getMENU()).apply(update);
            }
        }
        return null;
    }

    @SneakyThrows
    public void listStepAdd(Update update) {
        var commandLocal = update.getMessage().getText();
        var chatIdLocal = update.getMessage().getChatId();

        if (!configBean.listStep().containsKey(chatIdLocal)) {
            configBean.listStep().put(chatIdLocal, new ArrayDeque<>());
        }
        if (commandLocal.equals(command.getSTART()) || commandLocal.equals(command.getMENU())) {
            configBean.listStep().get(chatIdLocal).clear();
        }
        configBean.listStep().get(chatIdLocal).add(commandLocal);
    }
}
