package org.example.service.telegram.V2.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Panel {

    SendMessage inCommand(Update update);

}
