package org.example.service.telegram.V2.batton;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.config.Command;
import org.example.config.ConfigBean;
import org.example.dto.Data;
import org.example.dto.Dto;
import org.example.dto.DtoGeolocation;
import org.example.model.Order;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private Steep steep;

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
        configBean.map().put(command.getNOT_WORK(), this::notWork);
        configBean.map().put(command.getNOT_HOT(), this::notHot);
        configBean.map().put(command.getVERY_HOT(), this::veryHot);
        configBean.map().put(command.getNOT_WORK_CULLER(), this::notWorkCuller);
        configBean.map().put(command.getNOT_CLOSE(), this::notClouse);
        configBean.map().put(command.getNOT_OPEN(), this::notOpen);
        configBean.map().put(command.getNOT_PURE(), this::notPure);
        configBean.map().put(command.getNOT_DRAIN(), this::notDrain);
        configBean.map().put(command.getNOT_ROLLING(), this::notRouling);
        configBean.map().put(command.getNOES(), this::noes);
        configBean.map().put(command.getERROR(), this::error);
        configBean.map().put(command.getTECH(), this::tech);
        configBean.map().put(command.getTOK(), this::tok);
        configBean.map().put(command.getHANGAR_SMELL(), this::smell);
        configBean.map().put(command.getRUN_TO_ROOM(), this::runToRoom);
        configBean.map().put(command.getNOT_DRY(), this::notDry);
        configBean.map().put(command.getADD_TIME(), this::addTime);
        configBean.map().put(command.getMY_CONTACT(), this::contactMy);
        configBean.map().put(command.getCONTACT_SEND(), this::contactSend);
        configBean.map().put(command.getWRITE_MY_DATA(), this::writeMyData);
        configBean.map().put(command.getREDACT(), this::writeMyData);
        configBean.map().put(command.getPROCEED_NO(), this::writeMyData);
        configBean.map().put(command.getRefactorName(), this::refactorName);
        configBean.map().put(command.getRefactorPhone(), this::refactorPhone);
        configBean.map().put(command.getRefactorAddress(), this::refactorAddress);
        configBean.map().put(command.getPROCEED(), this::whatDeath);
        configBean.map().put(command.getGEOLOCATION(), this::geolocation);
        configBean.map().put("Заявка №*", this::myRequestTwo);
    }

    private SendMessage geolocation(Update update) {

        User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());
        if (userByChatId == null) {
            userByChatId = new User(null,
                    null,
                    null,
                    null,
                    null,
                    update.getMessage().getChatId(),
                    false,
                    null);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth("");
        DtoGeolocation dtoGeolocation = new DtoGeolocation();
        dtoGeolocation.setCount("1");
        dtoGeolocation.setLat(update.getMessage().getLocation().getLatitude().toString());
        dtoGeolocation.setLon(update.getMessage().getLocation().getLongitude().toString());
        dtoGeolocation.setRadius_meters("50");
        HttpEntity<?> httpEntity = new HttpEntity<>(dtoGeolocation, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange("https://suggestions.dadata.ru/suggestions/api/4_1/rs/geolocate/address",
                HttpMethod.POST,
                httpEntity,
                String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Dto dto = objectMapper.readValue(response.getBody(), Dto.class);
            Data data = dto.suggestions.get(0).data;
            String s = data.cityWithType + ", " +
                    data.cityDistrict + " " +
                    data.cityDistrictType + ", " +
                    data.streetWithType + ", " +
                    data.house;
            userByChatId.setAddres(s);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        userService.saveUser(userByChatId);

        String last = configBean.listStep().get(update.getMessage().getChatId()).getLast();
        return configBean.map().get(last).apply(update);
    }

    private SendMessage refactorAddress(Update update) {
        var list = new ArrayList<String>();
        list.add("Введите адрес");
        return CreateButtonEdit.creteButton(list);
    }

    private SendMessage refactorPhone(Update update) {
        var list = new ArrayList<String>();
        list.add("Введите номер");
        return CreateButtonEdit.creteButton(list);
    }

    private SendMessage refactorName(Update update) {
        var list = new ArrayList<String>();
        list.add("Введите имя");
        return CreateButtonEdit.creteButton(list);
    }

    private SendMessage contactSend(Update update) {

        User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());
        if (userByChatId == null) {
            userByChatId = new User(null,
                    update.getMessage().getContact().getFirstName(),
                    update.getMessage().getContact().getUserId(),
                    null,
                    update.getMessage().getContact().getPhoneNumber(),
                    update.getMessage().getChatId(),
                    false,
                    null);
        }
        userByChatId.setNumber(update.getMessage().getContact().getPhoneNumber());
        userByChatId.setFistName(update.getMessage().getContact().getFirstName());

        userService.saveUser(userByChatId);
        String last = configBean.listStep().get(update.getMessage().getChatId()).getLast();
        return configBean.map().get(last).apply(update);
    }

    private SendMessage addTime(Update update) {
        return lastMethod(update);
    }

    private SendMessage notDry(Update update) {
        return lastMethod(update);
    }

    private SendMessage runToRoom(Update update) {
        return lastMethod(update);
    }

    private SendMessage smell(Update update) {
        return lastMethod(update);
    }

    private SendMessage tok(Update update) {
        return lastMethod(update);
    }

    private SendMessage tech(Update update) {
        return lastMethod(update);
    }

    private SendMessage error(Update update) {
        return lastMethod(update);
    }

    private SendMessage noes(Update update) {
        return lastMethod(update);
    }

    private SendMessage notRouling(Update update) {
        return lastMethod(update);
    }

    private SendMessage notDrain(Update update) {
        return lastMethod(update);
    }

    private SendMessage notPure(Update update) {
        return lastMethod(update);
    }

    private SendMessage notOpen(Update update) {
        return lastMethod(update);
    }

    private SendMessage notClouse(Update update) {
        return lastMethod(update);
    }

    private SendMessage notWorkCuller(Update update) {
        return lastMethod(update);
    }

    private SendMessage veryHot(Update update) {
        return lastMethod(update);
    }

    private SendMessage notHot(Update update) {
        return lastMethod(update);
    }

    private SendMessage notWork(Update update) {
        return lastMethod(update);
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
    } // меню готово

    public SendMessage contactMy(Update update) {
        var list = new ArrayList<String>();

        User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());

        if (userByChatId == null) {
            userByChatId = new User();
            userByChatId.setAddres("неизвестен");
            userByChatId.setNumber("неизвестен");
            userByChatId.setFistName("неизвестно");
        }

        list.add("Ваши контакты: \n" + userByChatId.toString());
        list.add(command.getREDACT());
        list.add(command.getMENU());

        return CreateButtonAll.creteButton(list);
    } // мои контакты (не готово редактирование)

    public SendMessage sendRequest(Update update) {
        var list = new ArrayList<String>();
        User userByChatId = userService.getUserByChatId(update.getMessage().getChatId());
        if (userByChatId == null) {
            list.add("Пожалуйста, добавьте ваши данные");
            list.add(command.getCONTACT_SEND());
            list.add(command.getGEOLOCATION());
            list.add(command.getWRITE_MY_DATA());
            list.add(command.getBACK());
            return CreateButtonContactAndLocation.creteButton(list);
        }
        if (userByChatId.getNumber() != null && userByChatId.getAddres() != null && userByChatId.getFistName() != null) {
            list.add("Ваши данные: \n" + userByChatId.toString());
            list.add(command.getPROCEED()); // продолжать с данными которые имеем
            list.add(command.getPROCEED_NO()); // ввести новые данные
            list.add(command.getBACK());
            return CreateButtonAll.creteButton(list);
        }
        list.add("Ваши данные: \n" + userByChatId.toString() + "\n!!! необходимо ввести все данные !!!");
        list.add(command.getCONTACT_SEND());
        list.add(command.getGEOLOCATION());
        list.add(command.getWRITE_MY_DATA());
        list.add(command.getBACK());
        return CreateButtonContactAndLocation.creteButton(list);
    } // оставить заявку

    public SendMessage writeMyData(Update update) {
        var list = new ArrayList<String>();
        list.add("Вы можете изменить ваши данные");
        list.add(command.getCONTACT_SEND());
        list.add(command.getGEOLOCATION());
        list.add(command.getRefactorName());
        list.add(command.getRefactorAddress());
        list.add(command.getRefactorPhone());
        list.add(command.getBACK());
        return CreateButtonContactAndLocation.creteButton(list);
    } //

    @SneakyThrows
    public SendMessage whatDeath(Update update) {
        var list = new ArrayList<String>();
        list.add("Оставьте заявку, для этого вам нужно выбрать, что сломалось?");
        list.add(command.getHOB());
        list.add(command.getOVEN());
        list.add(command.getWASHING_MACHINE());
        list.add(command.getDRYER());
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
    } // оставить заявку

    @SneakyThrows
    public SendMessage myRequest(Update update) { // список заявок
        var user = userService.getUserByChatId(update.getMessage().getChatId());
        List<Order> list = new ArrayList<>();
        if (user != null) {
            list = user.getOrders();
        }
        var iterator = list.iterator();


        var stringMassive = new ArrayList<String>();
        if (list.isEmpty() || list.stream().noneMatch(Order::getFlag)) {

            stringMassive.add("У вас пока нет заявок");
        } else {
            stringMassive.add("Список ваших заявок:");
        }

        while (iterator.hasNext()) {
            var d = iterator.next();
            if (!d.getFlag()) {
                continue;
            }
            stringMassive.add("Заявка №" + d.getId() + "\n" + "статус - " + d.getStatus());
        }

        stringMassive.add(command.getMENU());

        return CreateButtonAll.creteButton(stringMassive);
    } //

    public SendMessage myRequestTwo(Update update) {

        Pattern compile = Pattern.compile("^Заявка №(.*)\\n+.*", Pattern.MULTILINE);
        Matcher matcher = compile.matcher(update.getMessage().getText());
        long l = 0L;
        if (matcher.find()) {
            l = Long.parseLong(matcher.group(1));
        }
        Order d = orderService.getOrderById(l);
        var stringMassive = new ArrayList<String>();
        var s = "Заявка №" + d.getId() + "\n" +
                "статус - " + d.getStatus() + "\n" +
                "цена за работу " + d.getPrice() + " руб" + "\n" +
                "описание ( " + d.getOpisanie() + " )" + "\n" +
                "дата заявки " + d.getTime();

        stringMassive.add(s);
        configBean.listStep().get(update.getMessage().getChatId()).add(command.getBACK());
        stringMassive.add(command.getBACK());
        return CreateButtonAll.creteButton(stringMassive);
    }


    @SneakyThrows
    public SendMessage callBack(Update update) {
        var list = new ArrayList<String>();
        list.add("Хотите связаться с мастером?");
        list.add(command.getCALL());
        list.add(command.getCALL_BACK());
        list.add(command.getBACK());
        return CreateButtonAll.creteButton(list);
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

        User user = userService.getUserByChatId(update.getMessage().getChatId());
        if (user == null || user.getNumber() == null) {
            list.add("Сначала добавьте номер телефона во вкладке <мои контакты>");
            list.add(command.getBACK());
            return CreateButtonAll.creteButton(list);
        }

        list.add("Мастер свяжется с вами в ближайшее время");


        orderService.saveOrder(new Order(null,
                null,
                null,
                null,
                null,
                false,
                "Ждет звонка",
                user));
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
        List<String> collect = new ArrayList<>(configBean.listStep().get(update.getMessage().getChatId()));
        Collections.reverse(collect);
        String s = collect.stream().limit(2).reduce((s1, s2) -> s2 + " - " + s1).orElse("Ошибка");

        list.add("Отправить заявку? \n" + s);
        list.add(command.getSEND());
        list.add(command.getBACK());

        //отправляем сообщение
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage send(Update update) {
        var list = new ArrayList<String>();
        list.add("Ваша заявка принята, \nмастер свяжется с вами для подтверждения заявки. \nВ пункте <мои заявки> можно уточнить ее статус");
        list.add(command.getMY_REQUEST());
        list.add(command.getMENU());

        List<String> collect = new ArrayList<>(configBean.listStep().get(update.getMessage().getChatId()));
        Collections.reverse(collect);
        String s = collect.stream().skip(1).limit(2).reduce((s1, s2) -> s2 + " - " + s1).orElse("Ошибка");

        Calendar now = Calendar.getInstance();

        orderService.saveOrder(new Order(null, now.getTime().toString(), "в обработке", null, 0, true, s, userService.getUserByChatId(update.getMessage().getChatId())));
        return CreateButtonAll.creteButton(list);
    }

    @SneakyThrows
    public SendMessage backMethod(Update update) {
        return steep.backMethod(update);
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

}
