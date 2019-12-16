package com.stocker.telegram.spring;

import com.netflix.discovery.EurekaClient;
import com.stocker.telegram.StockTelegramConfigurationForTest;
import com.stocker.telegram.exception.UnexpectedCommandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockTelegramConfigurationForTest.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockTelegramBotTest {

    @MockBean
    private EurekaClient eurekaClient;

    @MockBean
    private StockTelegramComponent stockTelegramComponent;

    @Autowired
    private StockTelegramBot stockTelegramBot;

    @Test
    public void getCommandName() throws UnexpectedCommandException {
        assertEquals("space at start should be skipped", "show", StockTelegramBot.getCommandName(" show"));
        assertEquals("multiple space at start should be skipped", "show", StockTelegramBot.getCommandName("  show"));
        assertEquals("first word should be taken", "show", StockTelegramBot.getCommandName("show t"));
        assertEquals("/ command char should be removed", "show", StockTelegramBot.getCommandName("/show t"));
    }

    @Test(expected = UnexpectedCommandException.class)
    public void getCommandNameEmptyMessage() throws UnexpectedCommandException {
        StockTelegramBot.getCommandName("");
    }

    @Test(expected = UnexpectedCommandException.class)
    public void getCommandNameNullMessage() throws UnexpectedCommandException {
        StockTelegramBot.getCommandName(null);
    }

    @Test(expected = UnexpectedCommandException.class)
    public void getCommandNameSpaceMessage() throws UnexpectedCommandException {
        StockTelegramBot.getCommandName(" ");
    }


    @Test
    public void findCommand() throws UnexpectedCommandException {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("show");
        assertNotNull(stockTelegramBot.findCommand(update));

        Mockito.when(message.getText()).thenReturn("oversell");
        assertNotNull(stockTelegramBot.findCommand(update));
    }
}