package org.example.service.telegram;

import lombok.SneakyThrows;
import org.example.config.Command;
import org.example.model.Order;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    private final Map<String, Supplier<SendMessage>> map = new HashMap<>();

    private final Map<Long, ArrayDeque<String>> listStep = new ConcurrentHashMap<>();


    private volatile int initFistMap = 0;

    public void createMethodMap() {
//        map.put(command.getBACK().intern(), this::backMethod);
        map.put(command.getREQUEST(), this::sendRequest);
        map.put(command.getSTART(), this::mainMenu);
        map.put(command.getCALLBACK(), this::callBack);
//        map.put(command.getMY_REQUEST(), this::myRequest);
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
        map.put(command.getCALL(), this::call);
        map.put(command.getCALL_BACK(), this::callBackPhone);
    }

    public SendMessage inCommand(Update update) {
        if (initFistMap == 0) {
            createMethodMap();
        }

        var commanda = update.getMessage().getText();

        if (commanda.equals(command.getBACK())) {
            return backMethod(update);
        }
        if (commanda.equals(command.getMY_REQUEST())) {
            return myRequest(update);
        }

        if (map.containsKey(commanda)) {
            listStepAdd(update);
            return map.get(commanda).get();
        } else {
            return null;
        }
    }


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

    @SneakyThrows
    public SendMessage mainMenu() {
        var list = new ArrayList<String>();
        list.add("Главное меню");
        list.add(command.getREQUEST());
        list.add(command.getMY_REQUEST());
        list.add(command.getCALLBACK());
        list.add(command.getLEAVE_FEEDBACK());
        return creteButton(list);
    }

    //оставить заявку с этого метода будет дергаться что сломалось
    @SneakyThrows
    public SendMessage sendRequest() {
        var list = new ArrayList<String>();
        list.add("Оставьте заявку, для этого вам нужно выбрать, что сломалось?");
        list.add(command.getHOB());
        list.add(command.getOVEN());
        list.add(command.getWASHING_MACHINE());
        list.add(command.getDRYER());
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage myRequest(Update update) {
        var user = userService.getUserByChatId(update.getMessage().getChatId());
        List<Order> list = new ArrayList<>();
        if (user != null) {
            list = user.getOrders();
        }
        var iterator = list.iterator();

        var stringMassive = new ArrayList<String>();
        stringMassive.add("Список ваших заявок");

        while (iterator.hasNext()) {
            var d = iterator.next();

            stringMassive.add("Заявка №" + d.getId());
        }
        stringMassive.add(command.getMENU());

        return creteButton(stringMassive);
    }

    @SneakyThrows
    public SendMessage callBack() {
        var list = new ArrayList<String>();
        list.add("Хотите связаться с мастером?");
        list.add(command.getCALL());
        list.add(command.getCALL_BACK());
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage leaveFeedback() {
        var list = new ArrayList<String>();
        list.add("Можете написать ваш отзыв");
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage hob() {
        var list = new ArrayList<String>();
        list.add("Что с вашей варочной панелью?");
        list.add(command.getNOT_ON());
        list.add(command.getAUTOMATA());
        list.add(command.getBURNER());
        list.add(command.getBURNING());
        list.add(command.getWORKING());
        list.add(command.getOTHER());
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage oven() {
        var list = new ArrayList<String>();
        list.add("Что с вашей духовкой?");
        list.add(command.getNOT_ON());
        list.add(command.getAUTOMATA());
        list.add(command.getNOT_WORK());
        list.add(command.getNOT_HOT());
        list.add(command.getVERY_HOT());
        list.add(command.getNOT_WORK_CULLER());
        list.add(command.getOTHER());
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage washingMachine() {
        var list = new ArrayList<String>();
        list.add("Что с вашей стиральной машиной?");
        list.add(command.getNOT_ON());
        list.add(command.getAUTOMATA());
        list.add(command.getNOT_CLOSE());
        list.add(command.getNOT_OPEN());
        list.add(command.getNOT_PURE());
        list.add(command.getNOT_DRAIN());
        list.add(command.getNOT_HOT());
        list.add(command.getNOT_ROLLING());
        list.add(command.getNOES());
        list.add(command.getTECH());
        list.add(command.getERROR());
        list.add(command.getTOK());
        list.add(command.getHANGAR_SMELL());
        list.add(command.getRUN_TO_ROOM());
        list.add(command.getOTHER());
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage dryer() {
        var list = new ArrayList<String>();
        list.add("Что с вашей сушильной машиной?");
        list.add(command.getNOT_ON());
        list.add(command.getAUTOMATA());
        list.add(command.getNOT_CLOSE());
        list.add(command.getNOT_OPEN());
        list.add(command.getTECH());
        list.add(command.getERROR());
        list.add(command.getADD_TIME());
        list.add(command.getWORKING());
        list.add(command.getOTHER());
        list.add(command.getBACK());
        return creteButton(list);
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
    private SendMessage callBackPhone() {
        var list = new ArrayList<String>();
        list.add("Мастер свяжется с вами в ближайшее время");
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    private SendMessage call() {
        var list = new ArrayList<String>();
        list.add("Вашего мастера зовут Владислав, \n его номер: +7 (906) 217-01-72 ");
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage lastMethod() {
        var list = new ArrayList<String>();
        list.add("Отправить заявку?");
        list.add(command.getSEND());
        list.add(command.getBACK());
        return creteButton(list);
    }

    @SneakyThrows
    public SendMessage send() {
        var list = new ArrayList<String>();
        list.add("Ваша заявка принята, в истории заявок можно уточнить ее статус");
        list.add(command.getMY_REQUEST());
        list.add(command.getMENU());
        return creteButton(list);
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
        if (commandLocal.equals(command.getSEND())) {
            dbStepAdd(update);
        }
    }

    @SneakyThrows
    public void dbStepAdd(Update update) {
        User user = userService.getUserByChatId(update.getMessage().getChatId());
        if (user == null) {
            user = new User();
//            user.setNumber(update.getMessage().getContact().getPhoneNumber());
//            user.setFistName(update.getMessage().getContact().getFirstName());
//            user.setUserTgId(update.getMessage().getContact().getUserId());
            user.setChatId(update.getMessage().getChatId());
            userService.saveUser(user);
            user = userService.getUserByChatId(update.getMessage().getChatId());
        }
        Order order = new Order(null,
                "в обработке",
                "Пока не заполнен",
                0,
                listStep.get(update.getMessage().getChatId()).toString(),
                user);

        orderService.saveOrder(order);
    }
}

