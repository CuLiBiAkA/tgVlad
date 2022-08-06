package org.example.service.telegram;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.config.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.sandbox.username}")
    private String username;
    @Value("${bot.sandbox.token}")
    private String token;

    @Autowired
    private Command command;

    private Deque<String> listStep = new ArrayDeque<>();

    private Integer dellBotMessage = 0;

    private Update update;

    private Map<String, Runnable> mapMethod = null;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());
        this.update = update;
        createMapMethod();

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (mapMethod.containsKey(message.getText().intern())) {
                mapMethod.get(message.getText().intern()).run();
            } else {
                deleteLastMessage();
            }
        } else {
            if (update.hasMessage())
                deleteLastMessage();
        }
    }

    //метод главного меню
    @SneakyThrows
    public void mainMenu() {
        //передаем 1 сообщения поясняющее что за меню, остальные кнопки по порядку
        creteButton(update, new String[]{"Главное меню", command.getREQUEST(), command.getMY_REQUEST(), command.getCALLBACK(), command.getLEAVE_FEEDBACK()});
        listStep.clear();
        listStep.add(command.getSTART());
    }

    //оставить заявку с этого метода будет дергаться что сломалось
    @SneakyThrows
    public void sendRequest() {
        creteButton(update, new String[]{"Оставьте заявку, для этого вам нужно выбрать, что сломалось?", command.getHOB(), command.getOVEN(), command.getWASHING_MACHINE(), command.getDRYER(), command.getBACK()});
        listStepAdd(command.getREQUEST());
    }

    @SneakyThrows
    public void myRequest() {
        creteButton(update, new String[]{"Список ваших заявок", command.getBACK()});
        listStepAdd(command.getMY_REQUEST());
    }

    @SneakyThrows
    public void callBack() {
        creteButton(update, new String[]{"Хотите связаться с мастером?", "Связаться", command.getBACK()});
        listStepAdd(command.getCALLBACK());
    }

    @SneakyThrows
    public void leaveFeedback() {
        creteButton(update, new String[]{"Можете написать ваш отзыв", command.getBACK()});
        listStepAdd(command.getLEAVE_FEEDBACK());
    }

    @SneakyThrows
    public void hob() {
        creteButton(update, new String[]{"Что с вашей варочной панелью?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
        listStepAdd(command.getHOB());
    }

    @SneakyThrows
    public void oven() {
        creteButton(update, new String[]{"Что с вашей духовкой?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
        listStepAdd(command.getOVEN());
    }

    @SneakyThrows
    public void washingMachine() {
        creteButton(update, new String[]{"Что с вашей стиральной машиной?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
        listStepAdd(command.getWASHING_MACHINE());
    }

    @SneakyThrows
    public void dryer() {
        creteButton(update, new String[]{"Что с вашей сушильной машиной?", command.getNOT_ON(), command.getAUTOMATA(), command.getBURNER(), command.getBURNING(), command.getWORKING(), command.getOTHER(), command.getBACK()});
        listStepAdd(command.getDRYER());
    }

    @SneakyThrows
    public void notOn() {
        lastMethod();
    }

    @SneakyThrows
    public void automata() {
        lastMethod();
    }

    @SneakyThrows
    public void burner() {
        lastMethod();
    }

    @SneakyThrows
    public void burning() {
        lastMethod();
    }

    @SneakyThrows
    public void working() {
        lastMethod();
    }

    @SneakyThrows
    public void other() {
        lastMethod();
    }

    @SneakyThrows
    public void lastMethod() {
        creteButton(update, new String[]{"Ваша заявка" + listStep.toString(), command.getSEND(), command.getBACK()});
        listStepAdd(command.getLAST_METHOD());
    }

    @SneakyThrows
    public void send() {
        creteButton(update, new String[]{"Ваша заявка №" + Math.random() + " в обработке", command.getMENU()});
    }

    //метод удаляет последние сообщения/команды что бы не хламили чат
    @SneakyThrows
    public void deleteLastMessage() {
        execute(DeleteMessage.builder()
                .messageId(update.getMessage().getMessageId())
                .chatId(update.getMessage().getChatId())
                .build());
    }

    //возвращение на одно меню назад
    @SneakyThrows
    public void backMethod() {
        listStep.removeLast();
        update.getMessage().setText(listStep.getLast());
        onUpdateReceived(update);
    }

    //метод служит для создания и отображения кнопок в зависимости от количества команд. пока ебаный ифно есть мысля разрулить в бесконечном стриме
    @SneakyThrows
    public void creteButton(Update update, String[] s) {
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
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText(s[0]);
        //отправляем сообщение
        deleteLastMessage();
        execute(sendMessage);
        dellBotMass();
        messageId=update.getMessage().getMessageId();
    }

    private Integer lastMess = 0;

    private Integer messageId = 0;

    @SneakyThrows
    public void dellBotMass() {
        messageId++;
        if (lastMess != 0) {
            var chatId = update.getMessage().getChatId();
            try {
                execute(DeleteMessage.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .build());
            } catch (TelegramApiException e) {
                dellBotMass();
            }
        }
        else {
            lastMess++;
        }
    }

    private void createMapMethod() {
        var map = new HashMap<String, Runnable>();
        map.put(command.getBACK().intern(), this::backMethod);
        map.put(command.getREQUEST().intern(), this::sendRequest);
        map.put(command.getSTART().intern(), this::mainMenu);
        map.put(command.getCALLBACK(), this::callBack);
        map.put(command.getMY_REQUEST(), this::myRequest);
        map.put(command.getLEAVE_FEEDBACK(), this::leaveFeedback);
        map.put(command.getHOB().intern(), this::hob);
        map.put(command.getOVEN().intern(), this::oven);
        map.put(command.getWASHING_MACHINE().intern(), this::washingMachine);
        map.put(command.getDRYER().intern(), this::dryer);
        map.put(command.getNOT_ON(), this::notOn);
        map.put(command.getAUTOMATA(), this::automata);
        map.put(command.getBURNER(), this::burner);
        map.put(command.getWORKING(), this::working);
        map.put(command.getOTHER(), this::other);
        map.put(command.getBURNING(), this::burning);
        map.put(command.getSEND(), this::send);
        map.put(command.getMENU(), this::mainMenu);

        this.mapMethod = map;
    }

    public void listStepAdd(String nameMethod) {
        if (!listStep.getLast().equals(nameMethod)) {
            listStep.add(nameMethod);
        }
    }

}
