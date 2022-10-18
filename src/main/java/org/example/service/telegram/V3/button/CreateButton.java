package org.example.service.telegram.V3.button;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateButton {


    @SneakyThrows
    static public SendMessage creteButtonSimple(ArrayList<String> s) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        int i = 0;
        while (i + 1 < s.size()) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(s.get(1 + i));
            keyboard.add(keyboardFirstRow);
            i++;
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.disableNotification();
        sendMessage.setText(s.get(0));
        return sendMessage;
    }

    @SneakyThrows
    static public SendMessage createButtonDataAndLocation(ArrayList<String> s) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
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
