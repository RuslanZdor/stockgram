package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.UnexpectedCommandException;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

/**
 * Default interface for command implementation
 */
public abstract class ICommandProcessor {
    public abstract void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback);

    public static String getText(Update update) {
        return update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
    }

    public static Message getMessage(Update update) {
        return update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
    }
}
