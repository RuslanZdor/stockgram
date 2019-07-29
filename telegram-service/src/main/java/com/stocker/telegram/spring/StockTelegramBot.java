package com.stocker.telegram.spring;

import com.stocker.telegram.spring.command.*;
import com.stocker.telegram.exception.UnexpectedCommandException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StockTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private OverSellCommand overSellCommand;

    @Autowired
    private ShowCompanyCommand showCommand;

    @Autowired
    private UnexpectedCommand unexpectedCommand;

    @Autowired
    private WatchListCommand watchListCommand;

    @Autowired
    private AddToWatchListCompanyCommand addToWatchListCompanyCommand;

    private Map<String, ICommandProcessor> commandMap = new HashMap<>();

    private SendMessage processorCallback(SendMessage message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error(e);
        }
        return message;
    }

    @PostConstruct
    public void init() {
        commandMap.put(OverSellCommand.COMMAND, overSellCommand);
        commandMap.put(ShowCompanyCommand.COMMAND, showCommand);
        commandMap.put(WatchListCommand.COMMAND, watchListCommand);
        commandMap.put(AddToWatchListCompanyCommand.COMMAND, addToWatchListCompanyCommand);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
        try {
            ICommandProcessor processor = findCommand(update);
            processor.process(update, m -> processorCallback(m));
        } catch (UnexpectedCommandException ex) {
            log.error(ex);
            unexpectedCommand.process(update, m -> processorCallback(m));
        }
    }

    /**
     * Extract first word as command for bot
     * @param data message
     * @return first word from @data
     */
    protected static String getCommandName(String data) throws UnexpectedCommandException {
        if (StringUtils.isBlank(data)) {
            throw new UnexpectedCommandException(data);
        }
        return splitMessage(data)[0];
    }

    public static String[] splitMessage(String message) {
        String data = message.replaceAll("^\\s*\\/*", "");
        return data.split("(\\s+|_|-)");
    }

    /**
     * Try to find command based on numan Message
     * @param update human from telegram
     * @return fount command for message
     * @throws UnexpectedCommandException in case when command is not found
     */
    protected ICommandProcessor findCommand(Update update) throws UnexpectedCommandException {
        String messageCommand = ICommandProcessor.getText(update);
        log.info(String.format("Processing %s", messageCommand));
        String command = getCommandName(messageCommand);
        if (commandMap.containsKey(command)) {
            return commandMap.get(command);
        } else {
            throw new UnexpectedCommandException(messageCommand);
        }
    }

    @Override
    public String getBotUsername() {
        return "stockTensorFlowBot";
    }

    @Override
    public String getBotToken() {
        return "675780010:AAFVsRd6l3t0Ixr8cjeMpapD28sUvJGnPSg";
    }
}