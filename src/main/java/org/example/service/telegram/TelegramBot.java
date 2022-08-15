package org.example.service.telegram;

import javassist.bytecode.analysis.Executor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.threadpool.FixedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.sandbox.username}")
    private String username;
    @Value("${bot.sandbox.token}")
    private String token;

    @Autowired
    private CreateButton button;

    private final Map<Long, Integer> mapForIncrement = new ConcurrentHashMap<>();

    private final Map<Long, Integer> mapForLastMessage = new ConcurrentHashMap<>();

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
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
                if (!update.hasMessage()) {
                    return;
                }
                Message message = update.getMessage();
                if (!message.hasText() && !message.hasContact()) {
                    deleteMessage(message);
                    return;
                }

                var sendMessagePerm = button.inCommand(update);

                if (sendMessagePerm != null) {
                    sendMessagePerm.setChatId(message.getChatId());
                    sendMessage(sendMessagePerm);
                    deleteMessage(message);
                    deleteMessageBot(message);
                } else {
                    deleteMessage(message);
                }
        });
//        executorService.shutdown();
    }

    public void deleteMessageBot(Message message) {
        if (!mapForLastMessage.containsKey(message.getChatId())) {
            mapForLastMessage.put(message.getChatId(), message.getMessageId());
            mapForIncrement.put(message.getChatId(), message.getMessageId());
        } else {
            mapForLastMessage.put(message.getChatId(), message.getMessageId());
        }
        int iter = 0;
        while (!mapForIncrement.get(message.getChatId()).equals(mapForLastMessage.get(message.getChatId()))) {
            iter++;
//            System.out.println("пытаюсь удалить сообщение бота итерация № " + iter);
            var messageId = mapForIncrement.get(message.getChatId());
            messageId++;
            try {
                execute(DeleteMessage
                        .builder()
                        .chatId(message.getChatId())
                        .messageId(messageId)
                        .build());
            } catch (TelegramApiException ignored) {
            }
            mapForIncrement.put(message.getChatId(), messageId);
        }
    }

    @SneakyThrows
    public void sendMessage(BotApiMethod<? extends Serializable> method) {
        execute(method);
    }

    @SneakyThrows
    public void deleteMessage(Message message) {
        execute(DeleteMessage
                .builder()
                .chatId(message.getChatId())
                .messageId(message.getMessageId())
                .build());
    }
}
