package com.stocker.telegram.command;

import com.stocker.telegram.exception.NoSymbolException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ShowCompanyCommand implements ICommandProcessor {

    public static final String COMMAND = "/show";

    @Override
    public void process(TelegramLongPollingBot bot, Message message, String data) {
    }

    /**
     * Extract company symbol from command
     * @param text for extraction
     * @return company Symbol
     * @throws NoSymbolException in case when there are no extraction
     */
    protected static String getSymbol(String text) throws NoSymbolException {
        String[] words = text.replaceAll("^\\s+", "").split("\\s+");
        if (words.length < 2 || words[1].length() == 0) {
            throw new NoSymbolException(String.format("command %s has no company symbol", text));
        }
        return words[1].toUpperCase();
    }
}
