package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ConfigBean {

    @Autowired
    private Command command;

    @Autowired
    private CommandAdmin commandAdmin;

    @Bean
    public Set<Long> adminSet() {
        return Set.of(537347100L,852564963L);
    } //537347100L 852564963L

    @Bean
    public Map<Long, ArrayDeque<String>> listStep() {
        return new HashMap<>();
    }

    @Bean
    public Map<String, Function<Update, SendMessage>> map(){
        return new HashMap<>();
    }

    @Bean
    public Map<String, Function<Update, SendMessage>> mapAdmin(){
        return new HashMap<>();
    }

    @Bean
    public List<String> commandList() {
        var classCommand = command.getClass();
        var fields = classCommand.getDeclaredFields();

        return Arrays.stream(fields)
                .map(field -> {
                    try {
                        return (String) field.get(command);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }


    @Bean
    public List<String> commandListAdmin() {
        var classCommand = commandAdmin.getClass();
        var fields = classCommand.getDeclaredFields();

        return Arrays.stream(fields)
                .map(field -> {
                    try {
                        return (String) field.get(commandAdmin);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }
}
