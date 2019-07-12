package com.stocker.telegram.command;

import com.stocker.telegram.exception.NoSymbolException;
import com.stocker.telegram.exception.UnexpectedCommandException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ShowCompanyCommand implements ICommandProcessor {

    public static final String COMMAND = "show";

    @Override
    public SendMessage process(Message message) throws UnexpectedCommandException {
        try {
            String symbol = getSymbol(message.getText());

            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChat().getId());
            sendMessage.setText(String.format("search for %s", symbol));

            return sendMessage;
        } catch (NoSymbolException e) {
            throw new UnexpectedCommandException(String.format("Bot wasn't found symbol in message: %s", message.getText()));
        }
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
