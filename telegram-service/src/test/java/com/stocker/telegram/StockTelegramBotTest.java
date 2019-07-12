package com.stocker.telegram;

import com.stocker.telegram.exception.UnexpectedCommandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StockTelegramConfigurationForTest.class})
public class StockTelegramBotTest {

    @Autowired
    private StockTelegramBot stockTelegramBot;

    @Test
    public void onUpdateReceived() {
    }

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
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("show");
        assertNotNull(stockTelegramBot.findCommand(message));

        Mockito.when(message.getText()).thenReturn("over_sell");
        assertNotNull(stockTelegramBot.findCommand(message));
    }
}