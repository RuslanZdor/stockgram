package com.stocker.telegram.spring.command;

import com.stocker.spring.client.CallbackDataClient;
import com.stocker.spring.client.UserDataClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddToWatchListCompanyCommand extends ICommandProcessor {

    public static final String COMMAND = "addToWatchList";

    private final UserDataClient userDataClient;

    private final CallbackDataClient callbackDataClient;

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

        Optional<String> findSymbol = getSymbol(getText(update));
        if (findSymbol.isPresent()) {
            String callbackId = findSymbol.get();
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
        } else {
            sendMessage.setText("Wrong message, company symbol is not found");
            callback.apply(sendMessage);
        }
    }

    /**
     * Extract company symbol from command
     *
     * @param text for extraction
     * @return company Symbol
     * Option.empty() in case when there are no extraction
     */
    private static Optional<String> getSymbol(String text) {
        String[] words = splitMessage(text);
        if (words.length < 2 || words[1].length() == 0) {
            return Optional.empty();
        }
        return Optional.of(words[1]);
    }
}
