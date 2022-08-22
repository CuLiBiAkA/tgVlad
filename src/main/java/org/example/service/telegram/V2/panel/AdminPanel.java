package org.example.service.telegram.V2.panel;

import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.service.telegram.V2.interfaces.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class AdminPanel implements Panel {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;



    public SendMessage inCommand(Update update){
        return null;
    }


}
