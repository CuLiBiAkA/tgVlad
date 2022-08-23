package org.example.service.telegram.V2.batton;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;



@Component
public class CreateButtonContactAndLocation {

    @SneakyThrows
    static public SendMessage creteButton(ArrayList<String> s) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setOneTimeKeyboard(false); // сворачивание клавы после действия

        List<KeyboardRow> keyboard = new ArrayList<>();

        int i = 0;

        KeyboardRow keyboardFirstRow1 = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText(s.get(1));
        keyboardFirstRow1.add(keyboardButton);
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setRequestLocation(true);
        keyboardButton1.setText(s.get(2));
        keyboardSecondRow.add(keyboardButton1);
        keyboard.add(keyboardFirstRow1);
        keyboard.add(keyboardSecondRow);



        while (i + 3 < s.size()) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(s.get(3 + i));
            keyboard.add(keyboardFirstRow);
            i++;
        }


        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.disableNotification();
        sendMessage.setText(s.get(0));
        return sendMessage;
    }


}
