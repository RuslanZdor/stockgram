package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.UnexpectedCommandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String, ICommandProcessor> commandMap = new HashMap<>();

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
        commandMap.put(OverSellCommand.COMMAND, overSellCommand);
        commandMap.put(ShowCompanyCommand.COMMAND, showCompanyCommand);
        commandMap.put(WatchListCommand.COMMAND, watchListCommand);
        commandMap.put(ViewCompanyCommand.COMMAND, viewCompanyCommand);
        commandMap.put(AddToWatchListCompanyCommand.COMMAND, addToWatchListCompanyCommand);
        commandMap.put(ShowStrategyCommand.COMMAND, showStrategyCommand);
    }

    /**
     * Try to find command based on human Message
     *
     * @param update human from telegram
     * @return fount command for message
     * @throws UnexpectedCommandException in case when command is not found
     */
    ICommandProcessor findCommand(Update update) throws UnexpectedCommandException {
        String messageCommand = ICommandProcessor.getText(update);
        log.info(String.format("Processing %s", messageCommand));
        String command = getCommandName(messageCommand);
        if (commandMap.containsKey(command)) {
            return commandMap.get(command);
        } else {
            throw new UnexpectedCommandException(messageCommand);
        }
    }
}
