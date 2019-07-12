package com.stocker.telegram.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class OverSellCommand implements ICommandProcessor {

    public static final String COMMAND = "over_sell";

    @Override
    public SendMessage process(Message message) {
        return null;
    }
}
