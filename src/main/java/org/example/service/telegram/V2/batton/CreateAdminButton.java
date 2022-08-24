package org.example.service.telegram.V2.batton;

import org.example.config.CommandAdmin;
import org.example.config.ConfigBean;
import org.example.model.Order;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CreateAdminButton {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ConfigBean configBean;

    @Autowired
    private CommandAdmin commandAdmin;

    @PostConstruct
    public void createMethodMap() {
//        configBean.mapAdmin().put(commandAdmin.getBACK().intern(), this::backMethod);
//        configBean.mapAdmin().put(commandAdmin.getREQUEST(), this::sendRequest);
        configBean.mapAdmin().put(commandAdmin.getSTART(), this::mainMenu);
        configBean.mapAdmin().put(commandAdmin.getMenu(), this::mainMenu);
        configBean.mapAdmin().put(commandAdmin.getA1(), this::mainMenu);
        configBean.mapAdmin().put(commandAdmin.getA2(), this::новыеЗаявки);
        configBean.mapAdmin().put(commandAdmin.getA3(), this::найтиЗаявку);
        configBean.mapAdmin().put(commandAdmin.getA4(), this::всеПользователи);
        configBean.mapAdmin().put(commandAdmin.getA5(), this::всеЗаявки);

    }

    private SendMessage всеЗаявки(Update update) {
        var list = new ArrayList<String>();
        Iterable<Order> allOrder = orderService.getAllOrder();
        Iterator<Order> iterator = allOrder.iterator();
        ArrayList<String> strings = new ArrayList<>();
        while (iterator.hasNext()) {
            Order next = iterator.next();
            strings.add(next.toString());
        }
        list.add("Все заявки: " + strings);
        list.add(commandAdmin.getMenu());
        return CreateButtonAll.creteButton(list);
    }

    private SendMessage всеПользователи(Update update) {
        var list = new ArrayList<String>();
        Iterable<User> allOrder = userService.getAllUser();
        Iterator<User> iterator = allOrder.iterator();
        ArrayList<String> strings = new ArrayList<>();
        while (iterator.hasNext()) {
            User next = iterator.next();
            strings.add(next.toString());
        }
        list.add("Все пользователи: " + strings);
        list.add(commandAdmin.getMenu());

        return CreateButtonAll.creteButton(list);
    }

    private SendMessage найтиЗаявку(Update update) {
        return null;
    }

    private SendMessage новыеЗаявки(Update update) {
        return null;
    }

    private SendMessage mainMenu(Update update) {
        var list = new ArrayList<String>();
        list.add("Главное меню Администратора");

        list.add(commandAdmin.getA1());
        list.add(commandAdmin.getA2());
        list.add(commandAdmin.getA3());
        list.add(commandAdmin.getA4());
        list.add(commandAdmin.getA5());

        return CreateButtonAll.creteButton(list);
    }
}
