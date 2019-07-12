package com.stocker.telegram;

import com.stocker.telegram.exception.UnexpectedCommandException;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockTelegramBotTest {

    @Test
    public void onUpdateReceived() {
    }

    @Test
    public void getCommandName() throws UnexpectedCommandException {
        assertEquals("space at start should be skipped", "show", StockTelegramBot.getCommandName(" show"));
        assertEquals("multiple space at start should be skipped", "show", StockTelegramBot.getCommandName("  show"));
        assertEquals("multiple space at start should be skipped", "show", StockTelegramBot.getCommandName("show t"));
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
    public void findCommand() {
    }
}