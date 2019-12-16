package com.stocker.telegram.spring.command;

import com.stocker.spring.ChartDataClient;
import com.stocker.spring.CompanyDataClient;
import com.stocker.telegram.exception.NoSymbolException;
import com.stocker.telegram.spring.StockTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@Slf4j
@Component
public class ViewCompanyCommand extends ICommandProcessor {

    public static final String COMMAND = "view";

    @Autowired
    private CompanyDataClient companyDataClient;

    @Autowired
    private ChartDataClient chartDataClient;

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
            companyDataClient.getCompany(symbol).single().subscribe(
                    company -> {
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(getMessage(update).getChatId());
                        sendPhoto.disableNotification();
                        sendPhoto.setPhoto(chartDataClient.getView(symbol));

                        callback.apply(sendPhoto);
                    },
                    error -> {
                        log.error("Telegram service failed get company information", error);
                        sendMessage.setText(String.format("Error happen during search request %s", symbol));
                        callback.apply(sendMessage);
                    },
                    () -> log.info("view finished")
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
    private static String getSymbol(String text) throws NoSymbolException {
        String[] words = StockTelegramBot.splitMessage(text);
        if (words.length < 2 || words[1].length() == 0) {
            throw new NoSymbolException(String.format("command %s has no company symbol", text));
        }
        return words[1].toUpperCase();
    }
}
