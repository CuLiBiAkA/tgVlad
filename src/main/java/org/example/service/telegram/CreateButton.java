//package org.example.service.telegram;
//
//import lombok.SneakyThrows;
//import org.example.config.Command;
//import org.example.model.Order;
//import org.example.model.User;
//import org.example.service.OrderService;
//import org.example.service.UserService;
//import org.example.service.telegram.V2.panel.AdminPanel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Supplier;
//
//@Service
//public class CreateButton {
//
//    @Autowired
//    private Command command;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private AdminPanel adminPanel;
//
//    private final Map<String, Supplier<SendMessage>> map = new HashMap<>();
//
//    private final Map<Long, ArrayDeque<String>> listStep = new ConcurrentHashMap<>();
//
//    public void createMethodMap() {
////        map.put(command.getBACK().intern(), this::backMethod);
//        map.put(command.getREQUEST(), this::sendRequest);
//        map.put(command.getSTART(), this::mainMenu);
//        map.put(command.getCALLBACK(), this::callBack);
////        map.put(command.getMY_REQUEST(), this::myRequest);
//        map.put(command.getLEAVE_FEEDBACK(), this::leaveFeedback);
//        map.put(command.getHOB(), this::hob);
//        map.put(command.getOVEN(), this::oven);
//        map.put(command.getWASHING_MACHINE(), this::washingMachine);
//        map.put(command.getDRYER(), this::dryer);
//        map.put(command.getNOT_ON(), this::notOn);
//        map.put(command.getAUTOMATA(), this::automata);
//        map.put(command.getBURNER(), this::burner);
//        map.put(command.getWORKING(), this::working);
//        map.put(command.getOTHER(), this::other);
//        map.put(command.getBURNING(), this::burning);
//        map.put(command.getSEND(), this::send);
//        map.put(command.getMENU(), this::mainMenu);
//        map.put(command.getCALL(), this::call);
//        map.put(command.getCALL_BACK(), this::callBackPhone);
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
//    }
//
//    private SendMessage addTime() {
//        return lastMethod();
//    }
//
//    private SendMessage notDry() {
//        return lastMethod();
//    }
//
//    private SendMessage runToRoom() {
//        return lastMethod();
//    }
//
//    private SendMessage smell() {
//        return lastMethod();
//    }
//
//    private SendMessage tok() {
//        return lastMethod();
//    }
//
//    private SendMessage tech() {
//        return lastMethod();
//    }
//
//    private SendMessage error() {
//        return lastMethod();
//    }
//
//    private SendMessage noes() {
//        return lastMethod();
//    }
//
//    private SendMessage notDrain() {
//        return lastMethod();
//    }
//
//    private SendMessage notRouling() {
//        return lastMethod();
//    }
//
//    private SendMessage notPure() {
//        return lastMethod();
//    }
//
//    private SendMessage notOpen() {
//        return lastMethod();
//    }
//
//    private SendMessage notClouse() {
//        return lastMethod();
//    }
//
//    private SendMessage notWorkCuller() {
//        return lastMethod();
//    }
//
//    private SendMessage veryHot() {
//        return lastMethod();
//    }
//
//    private SendMessage notHot() {
//        return lastMethod();
//    }
//
//    private SendMessage notWork() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    public SendMessage inCommand(Update update) {
//
//        if (map.isEmpty()) {
//            createMethodMap();
//        }
//
//        if (update.getMessage().hasContact()) {
//            if (listStep.get(update.getMessage().getChatId()).getLast().equals(command.getCALLBACK())) {
//                dbStepAdd(update);
//                return callBackPhone();
//            } else {
//                dbStepAdd(update);
//                return send();
//            }
//        }
//        if (update.getMessage().hasLocation()) {
//            return null;
//        }
//
//        var commanda = update.getMessage().getText();
//
//        if (commanda.equals(command.getMY_CONTACT())) {
//            return contactMy(update);
//        }
//        if (commanda.equals(command.getBACK())) {
//            return backMethod(update);
//        }
//        if (commanda.equals(command.getMY_REQUEST())) {
//            return myRequest(update);
//        }
//        if (map.containsKey(commanda)) {
//            listStepAdd(update);
//            return map.get(commanda).get();
//        } else {
//            return null;
//        }
//    }
//
//    @SneakyThrows
//    static public SendMessage creteButton(ArrayList<String> s) {
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        // Создаем клавиатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        // Создаем список строк клавиатуры
//        List<KeyboardRow> keyboard = new ArrayList<>();
//
//        int i = 0;
//        while (i + 1 < s.size()) {
//            KeyboardRow keyboardFirstRow = new KeyboardRow();
//            keyboardFirstRow.add(s.get(1 + i));
//            keyboard.add(keyboardFirstRow);
//            i++;
//        }
//        // и устанавливаем этот список нашей клавиатуре
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        // какой-то маркер
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        // дособираем ответочку
//        sendMessage.setText(s.get(0));
//        //отправляем сообщение
//        return sendMessage;
//    }
//
//    @SneakyThrows
//    public SendMessage mainMenu() {
//        var list = new ArrayList<String>();
//        list.add("Главное меню");
//        list.add(command.getREQUEST());
//        list.add(command.getMY_REQUEST());
//        list.add(command.getMY_CONTACT());
//        list.add(command.getCALLBACK());
//        return creteButton(list);
//    }
//
//    public SendMessage contactMy(Update update) {
//        var list = new ArrayList<String>();
//        var user = userService.getUserByChatId(update.getMessage().getChatId());
//        if (user == null) {
//            user = new User(null,
//                    update.getMessage().getFrom().getFirstName(),
//                    null,
//                    "пока не знаем",
//                    "пока не знаем",
//                    update.getMessage().getChatId(),
//                    false,
//                    null
//            );
//        }
//        list.add(user.toString());
//        list.add(command.getNAME());
//        list.add(command.getPHONE());
//        list.add(command.getADDRESS());
//        list.add(command.getMENU());
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        // Создаем клавиатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        // Создаем список строк клавиатуры
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        KeyboardRow keyboardThreeRow = new KeyboardRow();
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        KeyboardRow keyboardORRRow = new KeyboardRow();
//        KeyboardButton keyboardButton1 = new KeyboardButton();
//        keyboardButton1.setRequestContact(true);
//        KeyboardButton keyboardButton2 = new KeyboardButton();
//        keyboardButton2.setRequestContact(true);
//        KeyboardButton keyboardButton3 = new KeyboardButton();
//        keyboardButton3.setRequestLocation(true);
//        KeyboardButton keyboardButton4 = new KeyboardButton();
//
//        keyboardButton1.setText(list.get(1));
//        keyboardButton2.setText(list.get(2));
//        keyboardButton3.setText(list.get(3));
//        keyboardButton4.setText(list.get(4));
//        keyboardFirstRow.add(keyboardButton1);
//        keyboardSecondRow.add(keyboardButton2);
//        keyboardThreeRow.add(keyboardButton3);
//        keyboardORRRow.add(keyboardButton4);
//
//        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
//        keyboard.add(keyboardThreeRow);
//        keyboard.add(keyboardORRRow);
//        // и устанавливаем этот список нашей клавиатуре
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        // какой-то маркер
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        // дособираем ответочку
//        sendMessage.setText(list.get(0));
//        //отправляем сообщение
//        return sendMessage;
//
//    }
//
//    //оставить заявку с этого метода будет дергаться что сломалось
//    @SneakyThrows
//    public SendMessage sendRequest() {
//        var list = new ArrayList<String>();
//        list.add("Оставьте заявку, для этого вам нужно выбрать, что сломалось?");
//        list.add(command.getHOB());
//        list.add(command.getOVEN());
//        list.add(command.getWASHING_MACHINE());
//        list.add(command.getDRYER());
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage myRequest(Update update) {
//        var user = userService.getUserByChatId(update.getMessage().getChatId());
//        List<Order> list = new ArrayList<>();
//        if (user != null) {
//            list = user.getOrders();
//        }
//        var iterator = list.iterator();
//
//        var stringMassive = new ArrayList<String>();
//        stringMassive.add("Список ваших заявок");
//
//        while (iterator.hasNext()) {
//            var d = iterator.next();
//            if (!d.getFlag()) {
//                continue;
//            }
//            var s = "Заявка №" + d.getId() + " статус - " + d.getStatus();
//            stringMassive.add("Заявка №" + d.getId() + " статус - " + d.getStatus());
//        }
//        stringMassive.add(command.getMENU());
//
//        return creteButton(stringMassive);
//    }
//
//    @SneakyThrows
//    public SendMessage callBack() {
//        var list = new ArrayList<String>();
//        list.add("Хотите связаться с мастером?");
//        list.add(command.getCALL());
//        list.add(command.getCALL_BACK());
//        list.add(command.getBACK());
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        // Создаем клавиатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        // Создаем список строк клавиатуры
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        KeyboardButton keyboardButton = new KeyboardButton();
//        keyboardButton.setRequestContact(true);
//        keyboardButton.setText(list.get(2));
//        keyboardFirstRow.add(keyboardButton);
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        KeyboardRow keyboardThreeRow = new KeyboardRow();
//        keyboardSecondRow.add(list.get(1));
//        keyboardThreeRow.add(list.get(3));
//        keyboard.add(keyboardSecondRow);
//        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardThreeRow);
//        // и устанавливаем этот список нашей клавиатуре
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        // какой-то маркер
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        // дособираем ответочку
//        sendMessage.setText(list.get(0));
//        //отправляем сообщение
//        return sendMessage;
//    }
//
//    @SneakyThrows
//    public SendMessage leaveFeedback() {
//        var list = new ArrayList<String>();
//        list.add("Можете написать ваш отзыв");
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage hob() {
//        var list = new ArrayList<String>();
//        list.add("Что с вашей варочной панелью?");
//        list.add(command.getNOT_ON());
//        list.add(command.getAUTOMATA());
//        list.add(command.getBURNER());
//        list.add(command.getBURNING());
//        list.add(command.getWORKING());
//        list.add(command.getOTHER());
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage oven() {
//        var list = new ArrayList<String>();
//        list.add("Что с вашей духовкой?");
//        list.add(command.getNOT_ON());
//        list.add(command.getAUTOMATA());
//        list.add(command.getNOT_WORK());
//        list.add(command.getNOT_HOT());
//        list.add(command.getVERY_HOT());
//        list.add(command.getNOT_WORK_CULLER());
//        list.add(command.getOTHER());
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage washingMachine() {
//        var list = new ArrayList<String>();
//        list.add("Что с вашей стиральной машиной?");
//        list.add(command.getNOT_ON());
//        list.add(command.getAUTOMATA());
//        list.add(command.getNOT_CLOSE());
//        list.add(command.getNOT_OPEN());
//        list.add(command.getNOT_PURE());
//        list.add(command.getNOT_DRAIN());
//        list.add(command.getNOT_HOT());
//        list.add(command.getNOT_ROLLING());
//        list.add(command.getNOES());
//        list.add(command.getTECH());
//        list.add(command.getERROR());
//        list.add(command.getTOK());
//        list.add(command.getHANGAR_SMELL());
//        list.add(command.getRUN_TO_ROOM());
//        list.add(command.getOTHER());
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage dryer() {
//        var list = new ArrayList<String>();
//        list.add("Что с вашей сушильной машиной?");
//        list.add(command.getNOT_ON());
//        list.add(command.getAUTOMATA());
//        list.add(command.getNOT_CLOSE());
//        list.add(command.getNOT_OPEN());
//        list.add(command.getTECH());
//        list.add(command.getERROR());
//        list.add(command.getADD_TIME());
//        list.add(command.getWORKING());
//        list.add(command.getOTHER());
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage notOn() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    public SendMessage automata() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    public SendMessage burner() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    public SendMessage burning() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    public SendMessage working() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    public SendMessage other() {
//        return lastMethod();
//    }
//
//    @SneakyThrows
//    private SendMessage callBackPhone() {
//        var list = new ArrayList<String>();
//        list.add("Мастер свяжется с вами в ближайшее время");
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    private SendMessage call() {
//        var list = new ArrayList<String>();
//        list.add("Вашего мастера зовут Владислав, \n его номер: +7 (906) 217-01-72 ");
//        list.add(command.getBACK());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage lastMethod() {
//        var list = new ArrayList<String>();
//        list.add("Отправить заявку? \n" +
//                " \n при нажатии кнопки отправить мы попросим ваш номер");
//        list.add(command.getSEND());
//        list.add(command.getBACK());
//
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        // Создаем клавиатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        // Создаем список строк клавиатуры
//        List<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        KeyboardButton keyboardButton = new KeyboardButton();
////
//        keyboardButton.setRequestContact(true);
////        keyboardButton.getRequestContact();
//
//        keyboardButton.setText(list.get(1));
//
//        keyboardFirstRow.add(keyboardButton);
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        keyboardSecondRow.add(list.get(2));
//        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
//        // и устанавливаем этот список нашей клавиатуре
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        // какой-то маркер
//        replyKeyboardMarkup.setOneTimeKeyboard(true);
//        // дособираем ответочку
//        sendMessage.setText(list.get(0));
//        //отправляем сообщение
//        return sendMessage;
//    }
//
//    @SneakyThrows
//    public SendMessage send() {
//        var list = new ArrayList<String>();
//        list.add("Ваша заявка принята, \nмастер свяжется с вами для подтверждения заявки. \nВ пункте <<мои заявки>> можно уточнить ее статус");
//        list.add(command.getMY_REQUEST());
//        list.add(command.getMENU());
//        return creteButton(list);
//    }
//
//    @SneakyThrows
//    public SendMessage backMethod(Update update) {
//        if (listStep.containsKey(update.getMessage().getChatId()) &&
//                !listStep.get(update.getMessage().getChatId()).isEmpty() &&
//                !listStep.get(update.getMessage().getChatId()).equals(command.getSTART()) &&
//                !listStep.get(update.getMessage().getChatId()).equals(command.getMENU())) {
//
//            listStep.get(update.getMessage().getChatId()).removeLast();
//            try {
//                var lastCommandLocal = listStep.get(update.getMessage().getChatId()).getLast();
//                return map.get(lastCommandLocal).get();
//            } catch (Exception ignored) {
//                return mainMenu();
//            }
//        }
//        return null;
//    }
//
//    @SneakyThrows
//    public void listStepAdd(Update update) {
//        var commandLocal = update.getMessage().getText();
//        var chatIdLocal = update.getMessage().getChatId();
//
//        if (!listStep.containsKey(chatIdLocal)) {
//            listStep.put(chatIdLocal, new ArrayDeque<>());
//        }
//        if (commandLocal.equals(command.getSTART()) || commandLocal.equals(command.getMENU())) {
//            listStep.get(chatIdLocal).clear();
//        }
//        listStep.get(chatIdLocal).add(commandLocal);
//        if (commandLocal.equals(command.getSEND())) {
//            dbStepAdd(update);
//        }
//    }
//
//    @SneakyThrows
//    public void dbStepAdd(Update update) {
//        User user = userService.getUserByChatId(update.getMessage().getChatId());
//        if (user == null) {
//            user = new User();
//            user.setAddres("пока не знаем");
//            user.setNumber(update.getMessage().getContact().getPhoneNumber());
//            user.setFistName(update.getMessage().getFrom().getFirstName());
//            user.setUserTgId(update.getMessage().getContact().getUserId());
//            user.setChatId(update.getMessage().getChatId());
//            userService.saveUser(user);
//            user = userService.getUserByChatId(update.getMessage().getChatId());
//        }
//        var listRead = listStep.get(update.getMessage().getChatId());
//        var sms = listRead.toString();
//        boolean flag = false;
//        if (listRead.size() >= 3) {
//            flag = true;
//        }
//        var data = new Date().toString();
//
//        Order order = new Order(null,
//                data,
//                "в обработке",
//                null,
//                0,
//                flag,
//                sms,
//                user);
//        orderService.saveOrder(order);
//    }
//}
//
