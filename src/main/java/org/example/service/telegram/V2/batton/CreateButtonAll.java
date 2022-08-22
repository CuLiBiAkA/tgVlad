package org.example.service.telegram.V2.batton;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateButtonAll {
    @SneakyThrows
    static public SendMessage creteButton(ArrayList<String> s) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        // Создаем клавиатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        int i = 0;
        while (i + 1 < s.size()) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(s.get(1 + i));
            keyboard.add(keyboardFirstRow);
            i++;
        }
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        // какой-то маркер
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        // дособираем ответочку
        sendMessage.setText(s.get(0));
        //отправляем сообщение
        return sendMessage;
    }
}
