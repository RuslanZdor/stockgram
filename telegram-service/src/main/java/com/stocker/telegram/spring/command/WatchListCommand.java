package com.stocker.telegram.spring.command;

import com.stocker.telegram.data.User;
import com.stocker.telegram.spring.client.UserDataClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@Log4j2
@Component
public class WatchListCommand extends ICommandProcessor {

    public static final String COMMAND = "watchlist";

    @Autowired
    private UserDataClient userDataClient;

    /**
     * Show Watch list for user
     * Do Search request to stocker-data service to find saved watch list
     * In case of positive search - display list of following companies
     * In other case - create new user with empty list
     *
     * @param update  initial message from user
     * @param callback process message callback
     */
    @Override
    public void process(Update update, Function<PartialBotApiMethod<Message>, PartialBotApiMethod<Message>> callback) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(update.getMessage().getChatId());

        userDataClient.getUser(getMessage(update).getFrom().getId().toString()).single().subscribe(
                user -> {
                    sendMessage.setText(buildWatchList(user));
                    callback.apply(sendMessage);
                },
                error -> {
                    User  user = new User();
                    user.setTelegramId(getMessage(update).getFrom().getId().toString());
                    user.setName(getMessage(update).getFrom().getUserName());
                    user.setFirstName(getMessage(update).getFrom().getFirstName());
                    user.setLastName(getMessage(update).getFrom().getLastName());
                    userDataClient.addUser(user).subscribe();
                    sendMessage.setText("Empty watch list");
                    callback.apply(sendMessage);
                },
                () -> log.info(sendMessage.getText())
        );
    }

    /**
     * Generate message with watch list companies
     * @param user for search watchlist
     * @return text of watchlist message
     */
    protected String buildWatchList(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("Watch List \n");
        if (user.getWatchList().isEmpty()) {
            sb.append("Empty watch list");
        } else {
          for(String symbol : user.getWatchList()) {
              sb.append(String.format("Open /show\\_%s \n", symbol));
          }
        }
        return sb.toString();
    }
}
