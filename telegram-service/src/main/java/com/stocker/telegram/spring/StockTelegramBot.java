package com.stocker.telegram.spring;

import com.stocker.telegram.spring.command.ICommandProcessor;
import com.stocker.telegram.spring.command.OverSellCommand;
import com.stocker.telegram.spring.command.ShowCompanyCommand;
import com.stocker.telegram.exception.UnexpectedCommandException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StockTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private OverSellCommand overSellCommand;

    @Autowired
    private ShowCompanyCommand showCommand;

    private Map<String, ICommandProcessor> commandMap = new HashMap<>();

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        commandMap.put(OverSellCommand.COMMAND, overSellCommand);
        commandMap.put(ShowCompanyCommand.COMMAND, showCommand);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            ICommandProcessor processor = findCommand(update.getMessage());
            execute(processor.process(update.getMessage()));
        } catch (UnexpectedCommandException ex) {
            System.out.println(ex.getMessage());
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
        data = data.replaceAll("^\\s*\\/*", "");
        return data.split("\\s+")[0];
    }

    /**
     * Try to find command based on numan Message
     * @param message human from telegram
     * @return fount command for message
     * @throws UnexpectedCommandException in case when command is not found
     */
    protected ICommandProcessor findCommand(Message message) throws UnexpectedCommandException {
        String command = getCommandName(message.getText());
        if (commandMap.containsKey(command)) {
            return commandMap.get(command);
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