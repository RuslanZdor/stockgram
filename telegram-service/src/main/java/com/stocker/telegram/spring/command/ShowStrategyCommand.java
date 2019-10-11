package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.NoSymbolException;
import com.stocker.telegram.spring.StockTelegramBot;
import com.stocker.telegram.spring.callback.AbstractCallback;
import com.stocker.telegram.spring.callback.AddToWatchListCallback;
import com.stocker.telegram.spring.client.CallbackDataClient;
import com.stocker.telegram.spring.client.ChartDataClient;
import com.stocker.telegram.spring.client.CompanyDataClient;
import com.stocker.telegram.spring.client.StrategyResultDataClient;
import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Log4j2
@Component
public class ShowStrategyCommand extends ICommandProcessor {

    public static final String COMMAND = "strategy";

    @Autowired
    private StrategyResultDataClient strategyResultDataClient;

    /**
     * Show Company implementation
     * Do Search request to stocker-data service to find company by symbol
     * In case of positive search - company information will be present
     * In other case - search error message
     *
     * @param update   initial message from user
     * @param callback process message callback
     */
    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(getMessage(update).getChatId());

        try {
            String symbol = getSymbol(getText(update));

            strategyResultDataClient.getStrategyResult(symbol).subscribe(strategyResult -> {
                        sendMessage.disableNotification();
                        sendMessage.setText(strategyResult.getStrategyName());
                        callback.apply(sendMessage);
                    },
                    error -> {
                        sendMessage.setText(String.format("nothing was found for symbol %s", error.getMessage()));
                        callback.apply(sendMessage);
                    },
                    () -> log.info(sendMessage.getText())
            );
        } catch (NoSymbolException e) {
            sendMessage.setText(String.format("Bot wasn't found symbol in message: %s", getText(update)));
            callback.apply(sendMessage);
        }
    }

    /**
     * Extract company symbol from command
     *
     * @param text for extraction
     * @return company Symbol
     * @throws NoSymbolException in case when there are no extraction
     */
    protected static String getSymbol(String text) throws NoSymbolException {
        String[] words = StockTelegramBot.splitMessage(text);
        if (words.length < 2 || words[1].length() == 0) {
            throw new NoSymbolException(String.format("command %s has no company symbol", text));
        }
        return words[1].toUpperCase();
    }
}
