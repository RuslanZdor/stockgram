package com.stocker.telegram;

import com.stocker.telegram.command.ICommandProcessor;
import com.stocker.telegram.command.OverSellCommand;
import com.stocker.telegram.command.ShowCompanyCommand;
import com.stocker.telegram.exception.UnexpectedCommandException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class StockTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private OverSellCommand overSellCommand;

    @Autowired
    private ShowCompanyCommand showCommand;

    private Map<String, ICommandProcessor> commandMap = new HashMap<>();

    @PostConstruct
    public void init() {
        commandMap.put(OverSellCommand.COMMAND, overSellCommand);
        commandMap.put(ShowCompanyCommand.COMMAND, showCommand);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            ICommandProcessor processor = findCommand(update.getMessage());
            processor.process(this, update.getMessage(), update.getMessage().getText());
        } catch (UnexpectedCommandException ex) {
            System.out.println(ex.getMessage());
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
        data = data.replaceAll("^\\s+", "");
        return data.split("\\s+")[0];
    }

    /**
     * Try to find command based on numan Message
     * @param message human from telegram
     * @return fount command for message
     * @throws UnexpectedCommandException in case when command is not found
     */
    protected ICommandProcessor findCommand(Message message) throws UnexpectedCommandException {
        if (commandMap.containsKey(getCommandName(message.getText()))) {
            return commandMap.get(commandMap);
        } else {
            throw new UnexpectedCommandException(message.getText());
        }
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}