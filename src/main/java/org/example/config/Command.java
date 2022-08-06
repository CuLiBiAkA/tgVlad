package org.example.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Command {
    private  final String BOT_COMMAND = "bot_command";
    private  final String START = "/start";
    private  final String REQUEST = "оставить заявку";
    private  final String CALLBACK = "связаться с мастером";
    private  final String MY_REQUEST = "мои заявки";
    private  final String LEAVE_FEEDBACK = "оставить отзыв";
    private  final String HOB = "варочная панель";
    private  final String OVEN = "духовой шкаф";
    private  final String WASHING_MACHINE = "стиральная машина";
    private  final String DRYER = "сушильная машина";
    private  final String BACK = "назад";
    private  final String NOT_ON = "не включается";
    private  final String AUTOMATA = "выбивает автоматы";
    private  final String BURNER = "не греет конфорка";
    private  final String BURNING = "запах гари";
    private  final String WORKING = "постоянно работает";
    private  final String OTHER = "другое: ...";
    private  final String SEND = "отправить";
    private  final String LAST_METHOD = "ласт";
    private  final String MENU = "вернуться в меню";

}
