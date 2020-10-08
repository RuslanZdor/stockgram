package com.stocker.telegram.spring.command;

import com.telegram.api.ICommandProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@Component
public class OverSellCommand extends ICommandProcessor {

    public static final String COMMAND = "oversell";

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {

    }

    /**
     * command name
     * @return command name
     */
    public String getCommand() {
        return COMMAND;
    }

}
