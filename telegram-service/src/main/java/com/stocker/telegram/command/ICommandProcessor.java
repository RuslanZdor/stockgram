package com.stocker.telegram.command;

import com.stocker.telegram.exception.UnexpectedCommandException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICommandProcessor {
    SendMessage process(Message message) throws UnexpectedCommandException;
}
