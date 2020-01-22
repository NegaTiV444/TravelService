package com.negativ.travelServiceTelegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private CityService cityService;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;



//    public static void main(String[] args) {
//        ApiContextInitializer.init();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            Bot bot = new Bot();
//            if (bot.init()) {
//                telegramBotsApi.registerBot(bot);
//            } else
//                System.exit(1);
//        } catch (TelegramApiRequestException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                case "/help":
                    sendMsg(message, "Enter city name");
                    break;
                default:
                    try {
                        City city = cityService.loadCityByName(message.getText());
                        sendMsg(message, city.getName() + ": " + city.getDescription());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NotFoundException ex) {
                        sendMsg(message, ex.getMessage());
                    }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
