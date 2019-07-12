package com.stocker.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class StockTelegramComponent {

    @Autowired
    StockTelegramBot telegramBot;

    @PostConstruct
    public void startBot() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(telegramBot);
    }

    @PreDestroy
    public void stopBot() {
        telegramBot.onClosing();
    }
}
