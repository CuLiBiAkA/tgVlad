package org.example.service.telegram.V2.batton;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateButtonEdit {
    @SneakyThrows
    static public SendMessage creteButton(ArrayList<String> s) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(s.get(0));
        return sendMessage;
    }
}
