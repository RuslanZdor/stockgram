package com.stocker.telegram.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class OverSellCommand implements ICommandProcessor {

    public static final String COMMAND = "/over_sell";

    @Override
    public void process(TelegramLongPollingBot bot, Message message, String data) {
    }
}
