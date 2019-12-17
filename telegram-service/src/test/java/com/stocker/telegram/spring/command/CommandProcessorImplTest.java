package com.stocker.telegram.spring.command;

import com.stocker.spring.client.*;
import com.stocker.telegram.StockTelegramConfigurationForTest;
import com.stocker.telegram.exception.UnexpectedCommandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockTelegramConfigurationForTest.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommandProcessorImplTest {

    @MockBean
    private UserDataClient userDataClient;

    @MockBean
    private CallbackDataClient callbackDataClient;

    @MockBean
    private CompanyDataClient companyDataClient;

    @MockBean
    private ChartDataClient chartDataClient;

    @MockBean
    private StrategyResultDataClient strategyResultDataClient;

    @Autowired
    private CommandProcessorImpl commandProcessorImpl;

    @Test
    public void getCommandName() throws UnexpectedCommandException {
        assertEquals("space at start should be skipped", "show", CommandProcessorImpl.getCommandName(" show"));
        assertEquals("multiple space at start should be skipped", "show", CommandProcessorImpl.getCommandName("  show"));
        assertEquals("first word should be taken", "show", CommandProcessorImpl.getCommandName("show t"));
        assertEquals("/ command char should be removed", "show", CommandProcessorImpl.getCommandName("/show t"));
    }

    @Test(expected = UnexpectedCommandException.class)
    public void getCommandNameEmptyMessage() throws UnexpectedCommandException {
        CommandProcessorImpl.getCommandName("");
    }

    @Test(expected = UnexpectedCommandException.class)
    public void getCommandNameNullMessage() throws UnexpectedCommandException {
        CommandProcessorImpl.getCommandName(null);
    }

    @Test(expected = UnexpectedCommandException.class)
    public void getCommandNameSpaceMessage() throws UnexpectedCommandException {
        CommandProcessorImpl.getCommandName(" ");
    }


    @Test
    public void findCommand() throws UnexpectedCommandException {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("show");
        assertNotNull(commandProcessorImpl.findCommand(update));

        Mockito.when(message.getText()).thenReturn("oversell");
        assertNotNull(commandProcessorImpl.findCommand(update));
    }
}
