package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.NoSymbolException;
import com.stocker.telegram.spring.client.CallbackDataClient;
import com.stocker.telegram.spring.StockTelegramBot;
import com.stocker.telegram.spring.client.UserDataClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Component
public class AddToWatchListCompanyCommand extends ICommandProcessor {

    public static final String COMMAND = "addToWatchList";

    @Autowired
    private UserDataClient userDataClient;

    @Autowired
    private CallbackDataClient callbackDataClient;

    /**
     * Add  Company to watch list implementation
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
            String callbackId = getSymbol(getText(update));
            log.info(String.format("Calling callback %s", callbackId));
            callbackDataClient.getAddToWatchListCallback(callbackId).subscribe(abstractCallback -> {
                        log.info(String.format("Searching user %s", abstractCallback.getTelegramId()));
                        userDataClient.getUser(abstractCallback.getTelegramId()).subscribe(user -> {
                            user.getWatchList().add(abstractCallback.getSymbol());
                            user.setWatchList(user.getWatchList().stream().distinct().collect(Collectors.toList()));
                            userDataClient.addUser(user).subscribe(user1 -> {
                                sendMessage.setText(String.format("Symbol %s was added to watch list", abstractCallback.getSymbol()));
                                callback.apply(sendMessage);
                            });
                        });
                    }, error -> {
                        sendMessage.setText(String.format("nothing was found for callback %s", callbackId));
                        callback.apply(sendMessage);
                    },
                    () -> log.info(sendMessage.getText()));
        } catch (NoSymbolException e) {
            log.error(e);
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
        return words[1];
    }
}
