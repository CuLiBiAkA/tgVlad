package org.example.service.telegram;

import lombok.SneakyThrows;
import org.example.config.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
public class CreateButton {

    @Autowired
    private Command command;

    private final Map<String, Supplier<SendMessage>> map = new HashMap<>();

    private final Map<Long, ArrayDeque<String>> listStep = new ConcurrentHashMap<>();

    private volatile int initFistMap = 0;

    public void createMethodMap() {
//        map.put(command.getBACK().intern(), this::backMethod);
        map.put(command.getREQUEST(), this::sendRequest);
        map.put(command.getSTART(), this::mainMenu);
        map.put(command.getCALLBACK(), this::callBack);
        map.put(command.getMY_REQUEST(), this::myRequest);
        map.put(command.getLEAVE_FEEDBACK(), this::leaveFeedback);
        map.put(command.getHOB(), this::hob);
        map.put(command.getOVEN(), this::oven);
        map.put(command.getWASHING_MACHINE(), this::washingMachine);
        map.put(command.getDRYER(), this::dryer);
        map.put(command.getNOT_ON(), this::notOn);
        map.put(command.getAUTOMATA(), this::automata);
        map.put(command.getBURNER(), this::burner);
        map.put(command.getWORKING(), this::working);
        map.put(command.getOTHER(), this::other);
        map.put(command.getBURNING(), this::burning);
        map.put(command.getSEND(), this::send);
        map.put(command.getMENU(), this::mainMenu);
    }

    public SendMessage inCommand(Update update) {
        if (initFistMap == 0) {
            createMethodMap();
        }

        var commanda = update.getMessage().getText();

        if (commanda.equals(command.getBACK())) {
            return backMethod(update);
        }

        if (map.containsKey(commanda)) {
            listStepAdd(update);
            return map.get(commanda).get();
        } else {
            return null;
        }
    }


    @SneakyThrows
    static public SendMessage creteButton(String[] s) {
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

        if (s.length == 2) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboard.add(keyboardFirstRow);
        }

        if (s.length == 3) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[2]);
            keyboard.add(keyboardFirstRow);
        }

        if (s.length == 4) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[2]);
            keyboardFirstRow.add(s[3]);
            keyboard.add(keyboardFirstRow);
        }

        if (s.length == 5) {
            // Первая строчка клавиатуры
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            // Добавляем кнопки в первую строчку клавиатуры
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[3]);

            // Вторая строчка клавиатуры
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            // Добавляем кнопки во вторую строчку клавиатуры
            keyboardSecondRow.add(s[2]);
            keyboardSecondRow.add(s[4]);

            // Добавляем все строчки клавиатуры в список
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
        }

        if (s.length == 6) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[2]);
            keyboardFirstRow.add(s[3]);
            keyboardSecondRow.add(s[4]);
            keyboardSecondRow.add(s[5]);
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);

        }

        if (s.length == 7) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[2]);
            keyboardFirstRow.add(s[3]);
            keyboardSecondRow.add(s[4]);
            keyboardSecondRow.add(s[5]);
            keyboardSecondRow.add(s[6]);

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
        }

        if (s.length == 8) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            KeyboardRow keyboardTreeRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[2]);
            keyboardFirstRow.add(s[3]);
            keyboardSecondRow.add(s[4]);
            keyboardSecondRow.add(s[5]);
            keyboardTreeRow.add(s[6]);
            keyboardTreeRow.add(s[7]);

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardTreeRow);
        }

        if (s.length == 9) {
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            KeyboardRow keyboardSecondRow = new KeyboardRow();
            KeyboardRow keyboardTreeRow = new KeyboardRow();
            keyboardFirstRow.add(s[1]);
            keyboardFirstRow.add(s[2]);
            keyboardFirstRow.add(s[3]);
            keyboardSecondRow.add(s[4]);
            keyboardSecondRow.add(s[5]);
            keyboardTreeRow.add(s[6]);
            keyboardTreeRow.add(s[7]);
            keyboardTreeRow.add(s[8]);

            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            keyboard.add(keyboardTreeRow);
        }
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        // какой-то маркер
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        // дособираем ответочку
        sendMessage.setText(s[0]);
        //отправляем сообщение
        return sendMessage;
    }

    @SneakyThrows
    public SendMessage mainMenu() {
        //передаем 1 сообщения поясняющее, что за меню, остальные кнопки по порядку
        return creteButton(new String[]{"Главное меню", command.getREQUEST(), command.getMY_REQUEST(), command.getCALLBACK(), command.getLEAVE_FEEDBACK()});
    }

    //оставить заявку с этого метода будет дергаться что сломалось
    @SneakyThrows
    public SendMessage sendRequest() {
        return creteButton(new String[]{"Оставьте заявку, для этого вам нужно выбрать, что сломалось?", command.getHOB(), command.getOVEN(), command.getWASHING_MACHINE(), command.getDRYER(), command.getBACK()});
    }

    @SneakyThrows
    public SendMessage myRequest() {
        return creteButton(new String[]{"Список ваших заявок", command.getBACK()});
    }

    @SneakyThrows
    public SendMessage callBack() {
        return creteButton(new String[]{"Хотите связаться с мастером?", "Связаться", command.getBACK()});
    }

    @SneakyThrows
    public SendMessage leaveFeedback() {
        return creteButton(new String[]{"Можете написать ваш отзыв", command.getBACK()});
    }

    @SneakyThrows
    public SendMessage hob() {
        return creteButton(new String[]{"Что с вашей варочной панелью?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
    }

    @SneakyThrows
    public SendMessage oven() {
        return creteButton(new String[]{"Что с вашей духовкой?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
    }

    @SneakyThrows
    public SendMessage washingMachine() {
        return creteButton(new String[]{"Что с вашей стиральной машиной?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
    }

    @SneakyThrows
    public SendMessage dryer() {
        return creteButton(new String[]{"Что с вашей сушильной машиной?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
    }

    @SneakyThrows
    public SendMessage notOn() {
        return lastMethod();
    }

    @SneakyThrows
    public SendMessage automata() {
        return lastMethod();
    }

    @SneakyThrows
    public SendMessage burner() {
        return lastMethod();
    }

    @SneakyThrows
    public SendMessage burning() {
        return lastMethod();
    }

    @SneakyThrows
    public SendMessage working() {
        return lastMethod();
    }

    @SneakyThrows
    public SendMessage other() {
        return lastMethod();
    }

    @SneakyThrows
    public SendMessage lastMethod() {
        return creteButton(new String[]{"Ваша заявка", command.getSEND(), command.getBACK()});

    }

    @SneakyThrows
    public SendMessage send() {
        return creteButton(new String[]{"Ваша заявка №" + Math.random() + " в обработке", command.getMENU()});
    }

    @SneakyThrows
    public SendMessage backMethod(Update update) {
        if (listStep.containsKey(update.getMessage().getChatId()) &&
                !listStep.get(update.getMessage().getChatId()).isEmpty() &&
                !listStep.get(update.getMessage().getChatId()).equals(command.getSTART()) &&
                !listStep.get(update.getMessage().getChatId()).equals(command.getMENU())) {

            listStep.get(update.getMessage().getChatId()).removeLast();

            var lastCommandLocal = listStep.get(update.getMessage().getChatId()).getLast();

            return map.get(lastCommandLocal).get();
        }
        return null;
    }

    @SneakyThrows
    public void listStepAdd(Update update) {
        var commandLocal = update.getMessage().getText();
        var chatIdLocal = update.getMessage().getChatId();

        if (!listStep.containsKey(chatIdLocal)) {
            listStep.put(chatIdLocal, new ArrayDeque<>());
        }
        if (commandLocal.equals(command.getSTART()) || commandLocal.equals(command.getMENU())) {
            listStep.get(chatIdLocal).clear();
        }
        listStep.get(chatIdLocal).add(commandLocal);
    }
}
