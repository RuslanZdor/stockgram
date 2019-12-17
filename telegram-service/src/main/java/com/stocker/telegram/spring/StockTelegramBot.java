package com.stocker.telegram.spring;

import com.stocker.telegram.spring.command.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AllArgsConstructor
public class StockTelegramBot extends TelegramLongPollingBot {

    private final ICommandProcessor commandProcessorImpl;

    private PartialBotApiMethod<Message> processorCallback(PartialBotApiMethod<Message> message) {
        try {
            if (message instanceof SendPhoto) {
                this.execute((SendPhoto) message);
            } else if (message instanceof SendMessage) {
                this.execute((SendMessage) message);
            } else if (message instanceof SendDocument) {
                this.execute((SendDocument) message);
            } else {
                throw new TelegramApiException("Unexpected type of object");
            }
        } catch (TelegramApiException e) {
            log.error("Failed to communicate with telegram API", e);
        }
        return message;
    }

    @Override
    public void onUpdateReceived(Update update) {
        commandProcessorImpl.process(update, this::processorCallback);
    }

    @Override
    public String getBotUsername() {
        return "stockTensorFlowBot";
    }

    @Override
    public String getBotToken() {
        return "675780010:AAF_PDdmbF1oexPs5VqdUvKbSwA5SRNo7aM";
    }
}