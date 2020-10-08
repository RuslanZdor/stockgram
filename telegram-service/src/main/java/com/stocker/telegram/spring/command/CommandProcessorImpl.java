package com.stocker.telegram.spring.command;

import com.telegram.api.ICommandProcessor;
import com.telegram.api.exception.UnexpectedCommandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandProcessorImpl extends ICommandProcessor {

    private final ICommandProcessor overSellCommand;
    private final ICommandProcessor showCompanyCommand;
    private final ICommandProcessor viewCompanyCommand;
    private final ICommandProcessor unexpectedCommand;
    private final ICommandProcessor watchListCommand;
    private final ICommandProcessor showStrategyCommand;
    private final ICommandProcessor addToWatchListCompanyCommand;

    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        try {
            ICommandProcessor processor = findCommand(update);
            processor.process(update, callback);
        } catch (UnexpectedCommandException ex) {
            log.error("Wrong command to process", ex);
            unexpectedCommand.process(update, callback);
        }
    }

    @PostConstruct
    public void init() {
        registerCommand(overSellCommand);
        registerCommand(showCompanyCommand);
        registerCommand(watchListCommand);
        registerCommand(viewCompanyCommand);
        registerCommand(addToWatchListCompanyCommand);
        registerCommand(showStrategyCommand);
    }

    /**
     * command name
     * @return command name
     */
    public String getCommand() {
        return "implementation";
    }

}
