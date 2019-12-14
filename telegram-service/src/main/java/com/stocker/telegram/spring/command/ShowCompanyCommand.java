package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.NoSymbolException;
import com.stocker.telegram.spring.client.CallbackDataClient;
import com.stocker.telegram.spring.client.ChartDataClient;
import com.stocker.telegram.spring.StockTelegramBot;
import com.stocker.telegram.spring.callback.AbstractCallback;
import com.stocker.telegram.spring.callback.AddToWatchListCallback;
import com.stocker.telegram.spring.client.YahooDataClient;
import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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
public class ShowCompanyCommand extends ICommandProcessor {

    public static final String COMMAND = "show";

    @Autowired
    private CompanyDataClient companyDataClient;

    @Autowired
    private CallbackDataClient callbackDataClient;

    @Autowired
    private ChartDataClient chartDataClient;

    @Autowired
    private YahooDataClient yahooDataClient;

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

            yahooDataClient.updateCompany(symbol).subscribe(updatedCompany ->
                    companyDataClient.getCompany(symbol).single().subscribe(
                            company -> {
                                sendMessage.setText(String.format("found for %s", company.getName()));

                                AddToWatchListCallback toWatchListCallback = new AddToWatchListCallback();
                                toWatchListCallback.setSymbol(company.getSymbol());
                                toWatchListCallback.setTelegramId(getMessage(update).getFrom().getId().toString());
                                toWatchListCallback.setId(UUID.randomUUID().toString());
                                callbackDataClient.addCallback(toWatchListCallback).subscribe(abstractCallback -> {
                                    setCompanyInformation(sendMessage, company, abstractCallback);
                                    callback.apply(sendMessage);
                                });

                                SendPhoto sendPhoto = new SendPhoto();
                                sendPhoto.setChatId(getMessage(update).getChatId());
                                sendPhoto.setPhoto(chartDataClient.getCompany(symbol));

                                callback.apply(sendPhoto);
                            },
                            error -> {
                                sendMessage.setText(String.format("nothing was found for symbol %s", symbol));
                                callback.apply(sendMessage);
                            },
                            () -> log.info(sendMessage.getText())
                    ));
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

    private void setCompanyInformation(SendMessage sendMessage, Company company, AbstractCallback abstractCallback) {
        if (company.getDays().isEmpty()) {
            sendMessage.setText(String.format("No daily information about this company %s", company.getSymbol()));
        } else {
            String sb = String.format("Current Price: %s \n", company.getDays().last().getPrice()) +
                    "Short period parameters\n" +
                    String.format("5 days EMA: %s\n", company.getDays().last().getEMA5()) +
                    String.format("5 days RSI: %s\n", company.getDays().last().getRSI5()) +
                    "Long period parameters\n" +
                    String.format("30 days EMA: %s\n", company.getDays().last().getEMA50()) +
                    String.format("30 days RSI: %s\n", company.getDays().last().getRSI50());
            sendMessage.setText(sb);
        }

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowsInline.add(rowInline);

        rowInline.add(new InlineKeyboardButton().setText("Add to Watch List").setCallbackData(String.format("%s %s", AddToWatchListCompanyCommand.COMMAND, abstractCallback.getId())));
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
    }
}
