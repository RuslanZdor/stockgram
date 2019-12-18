package com.stocker.telegram.spring.command;

import com.stocker.spring.client.StrategyResultDataClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowStrategyCommand extends ICommandProcessor {

    public static final String COMMAND = "strategy";

    private final StrategyResultDataClient strategyResultDataClient;

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

        Optional<String> findSymbol = getSymbol(getText(update));
        findSymbol.ifPresent(symbol -> strategyResultDataClient.getStrategyResult(symbol).subscribe(strategyResult -> {
                    sendMessage.disableNotification();
                    sendMessage.setText(String.format("Open /show\\_%s \n", strategyResult.getSymbol()));
                    callback.apply(sendMessage);
                },
                error -> {
                    log.error("Telegram service failed get strategy information", error);
                    sendMessage.setText(String.format("nothing was found for symbol %s", error.getMessage()));
                    callback.apply(sendMessage);
                },
                () -> log.info(sendMessage.getText())
        ));
        if (!findSymbol.isPresent()) {
            sendMessage.setText(String.format("Bot wasn't found symbol in message: %s", getText(update)));
            callback.apply(sendMessage);
        }
    }

    /**
     * Extract company symbol from command
     *
     * @param text for extraction
     * @return company Symbol
     */
    private static Optional<String> getSymbol(String text) {
        String[] words = splitMessage(text);
        if (words.length < 2 || words[1].length() == 0) {
            return Optional.empty();
        }
        return Optional.of(words[1].toUpperCase());
    }
}
