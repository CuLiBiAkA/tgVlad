package org.example.service.telegram;

import org.example.config.ConfigBean;
import org.example.service.telegram.V2.panel.AdminPanel;
import org.example.service.telegram.V2.panel.UserPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CreateButtonRequest {

    @Autowired
    private AdminPanel adminPanel;

    @Autowired
    private UserPanel userPanel;

    @Autowired
    private ConfigBean configBean;

    public SendMessage inCommand(Update update) {

        if (configBean.adminSet().contains(update.getMessage().getChatId())) {
            return adminPanel.inCommand(update);
        }
        else {
            return userPanel.inCommand(update);
        }
    }
}