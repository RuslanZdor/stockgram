package com.stocker.telegram.command;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICommandProcessor {
    void process(TelegramLongPollingBot bot, Message message, String data);
}
