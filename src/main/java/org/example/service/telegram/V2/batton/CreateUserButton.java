package org.example.service.telegram.V2.batton;

import lombok.SneakyThrows;
import org.example.config.Command;
import org.example.config.ConfigBean;
import org.example.model.Order;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class CreateUserButton {

    @Autowired
    private Command command;

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void createMethodMap() {
       configBean.map().put(command.getBACK().intern(), this::backMethod);
        configBean.map().put(command.getREQUEST(), this::sendRequest);
        configBean.map().put(command.getSTART(), this::mainMenu);
        configBean.map().put(command.getCALLBACK(), this::callBack);
        configBean.map().put(command.getMY_REQUEST(), this::myRequest);
        configBean.map().put(command.getLEAVE_FEEDBACK(), this::leaveFeedback);
        configBean.map().put(command.getHOB(), this::hob);
        configBean.map().put(command.getOVEN(), this::oven);
        configBean.map().put(command.getWASHING_MACHINE(), this::washingMachine);
        configBean.map().put(command.getDRYER(), this::dryer);
        configBean.map().put(command.getNOT_ON(), this::notOn);
        configBean.map().put(command.getAUTOMATA(), this::automata);
        configBean.map().put(command.getBURNER(), this::burner);
        configBean.map().put(command.getWORKING(), this::working);
        configBean.map().put(command.getOTHER(), this::other);
        configBean.map().put(command.getBURNING(), this::burning);
        configBean.map().put(command.getSEND(), this::send);
        configBean.map().put(command.getMENU(), this::mainMenu);
        configBean.map().put(command.getCALL(), this::call);
        configBean.map().put(command.getCALL_BACK(), this::callBackPhone);
//        map.put(command.getNOT_WORK(), this::notWork);
//        map.put(command.getNOT_HOT(), this::notHot);
//        map.put(command.getVERY_HOT(), this::veryHot);
//        map.put(command.getNOT_WORK_CULLER(), this::notWorkCuller);
//        map.put(command.getNOT_CLOSE(), this::notClouse);
//        map.put(command.getNOT_OPEN(), this::notOpen);
//        map.put(command.getNOT_PURE(), this::notPure);
//        map.put(command.getNOT_DRAIN(), this::notDrain);
//        map.put(command.getNOT_ROLLING(), this::notRouling);
//        map.put(command.getNOES(), this::noes);
//        map.put(command.getERROR(), this::error);
//        map.put(command.getTECH(), this::tech);
//        map.put(command.getTOK(), this::tok);
//        map.put(command.getHANGAR_SMELL(), this::smell);
//        map.put(command.getRUN_TO_ROOM(), this::runToRoom);
//        map.put(command.getNOT_DRY(), this::notDry);
//        map.put(command.getADD_TIME(), this::addTime);
    }


    @SneakyThrows
    public SendMessage mainMenu(Update update) {
        var list = new ArrayList<String>();
        list.add("Главное меню");
        list.add(command.getREQUEST());
        list.add(command.getMY_REQUEST());
        list.add(command.getMY_CONTACT());
        list.add(command.getCALLBACK());
        return CreateButtonAll.creteButton(list);
    }

    public SendMessage contactMy(Update update) {
        var list = new ArrayList<String>();
        list.add("Ваши контакты");
        return CreateButtonAll.creteButton(list);

    }

    @SneakyThrows
    public SendMessage sendRequest(Update update) {
        var list = new ArrayList<String>();
        list.add("Оставьте заявку, для этого вам нужно выбрать, что сломалось?");
        list.add(command.getHOB());
        list.add(command.getOVEN());
        list.add(command.getWASHING_MACHINE());
        list.add(command.getDRYER());
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
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
            if (!d.getFlag()) {
                continue;
            }
            var s = "Заявка №" + d.getId() + " статус - " + d.getStatus();
            stringMassive.add("Заявка №" + d.getId() + " статус - " + d.getStatus());
        }
        stringMassive.add(command.getMENU());

        return CreateButtonAll.creteButton(stringMassive);
    }

    @SneakyThrows
    public SendMessage callBack(Update update) {
        var list = new ArrayList<String>();
        list.add("Хотите связаться с мастером?");
        list.add(command.getCALL());
        list.add(command.getCALL_BACK());
        list.add(command.getBACK());

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
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText(list.get(2));
        keyboardFirstRow.add(keyboardButton);
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThreeRow = new KeyboardRow();
        keyboardSecondRow.add(list.get(1));
        keyboardThreeRow.add(list.get(3));
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardThreeRow);
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        // какой-то маркер
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        // дособираем ответочку
        sendMessage.setText(list.get(0));
        //отправляем сообщение
        return sendMessage;
    }

    @SneakyThrows
    public SendMessage leaveFeedback(Update update) {
        var list = new ArrayList<String>();
        list.add("Можете написать ваш отзыв");
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage hob(Update update) {
        var list = new ArrayList<String>();
        list.add("Что с вашей варочной панелью?");
        list.add(command.getNOT_ON());
        list.add(command.getAUTOMATA());
        list.add(command.getBURNER());
        list.add(command.getBURNING());
        list.add(command.getWORKING());
        list.add(command.getOTHER());
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage oven(Update update) {
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
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage washingMachine(Update update) {
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
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage dryer(Update update) {
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
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage notOn(Update update) {
        return lastMethod(update);
    }

    @SneakyThrows
    public SendMessage automata(Update update) {
        return lastMethod(update);
    }

    @SneakyThrows
    public SendMessage burner(Update update) {
        return lastMethod(update);
    }

    @SneakyThrows
    public SendMessage burning(Update update) {
        return lastMethod(update);
    }

    @SneakyThrows
    public SendMessage working(Update update) {
        return lastMethod(update);
    }

    @SneakyThrows
    public SendMessage other(Update update) {
        return lastMethod(update);
    }

    @SneakyThrows
    private SendMessage callBackPhone(Update update) {
        var list = new ArrayList<String>();
        list.add("Мастер свяжется с вами в ближайшее время");
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    private SendMessage call(Update update) {
        var list = new ArrayList<String>();
        list.add("Вашего мастера зовут Владислав, \n его номер: +7 (906) 217-01-72 ");
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage lastMethod(Update update) {
        var list = new ArrayList<String>();
        list.add("Отправить заявку? \n" +
                " \n при нажатии кнопки отправить мы попросим ваш номер");
        list.add(command.getSEND());
        list.add(command.getBACK());

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
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
//
        keyboardButton.setRequestContact(true);
//        keyboardButton.getRequestContact();

        keyboardButton.setText(list.get(1));

        keyboardFirstRow.add(keyboardButton);
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(list.get(2));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанавливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        // какой-то маркер
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        // дособираем ответочку
        sendMessage.setText(list.get(0));
        //отправляем сообщение
        return sendMessage;
    }

    @SneakyThrows
    public SendMessage send(Update update) {
        var list = new ArrayList<String>();
        list.add("Ваша заявка принята, \nмастер свяжется с вами для подтверждения заявки. \nВ пункте <<мои заявки>> можно уточнить ее статус");
        list.add(command.getMY_REQUEST());
        list.add(command.getMENU());
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage backMethod(Update update) {
        if (configBean.listStep().containsKey(update.getMessage().getChatId()) &&
                !configBean.listStep().get(update.getMessage().getChatId()).isEmpty() &&
                !configBean.listStep().get(update.getMessage().getChatId()).equals(command.getSTART()) &&
                !configBean.listStep().get(update.getMessage().getChatId()).equals(command.getMENU())) {

            configBean.listStep().get(update.getMessage().getChatId()).removeLast();
            try {
                var lastCommandLocal = configBean.listStep().get(update.getMessage().getChatId()).getLast();
                return configBean.map().get(lastCommandLocal).apply(update);
            } catch (Exception ignored) {
                return mainMenu(update);
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
        if (commandLocal.equals(command.getSEND())) {
            dbStepAdd(update);
        }
    }

    @SneakyThrows
    public void dbStepAdd(Update update) {
        User user = userService.getUserByChatId(update.getMessage().getChatId());
        if (user == null) {
            user = new User();
            user.setAddres("пока не знаем");
            user.setNumber(update.getMessage().getContact().getPhoneNumber());
            user.setFistName(update.getMessage().getFrom().getFirstName());
            user.setUserTgId(update.getMessage().getContact().getUserId());
            user.setChatId(update.getMessage().getChatId());
            userService.saveUser(user);
            user = userService.getUserByChatId(update.getMessage().getChatId());
        }
        var listRead = configBean.listStep().get(update.getMessage().getChatId());
        var sms = listRead.toString();
        boolean flag = false;
        if (listRead.size() >= 3) {
            flag = true;
        }
        var data = new Date().toString();

        Order order = new Order(null,
                data,
                "в обработке",
                null,
                0,
                flag,
                sms,
                user);
        orderService.saveOrder(order);
    }

    public SendMessage start(Update update) {
       return configBean.map().get(update.getMessage().getText()).apply(update);
    }
}
