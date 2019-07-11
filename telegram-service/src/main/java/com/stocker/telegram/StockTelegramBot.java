package com.stocker.telegram;

import com.stocker.telegram.command.ICommandProcessor;
import com.stocker.telegram.command.OverSellCommand;
import com.stocker.telegram.command.ShowCompanyCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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

    }

    private String getCommandName(String data) {
        return data.split(" ")[0];
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