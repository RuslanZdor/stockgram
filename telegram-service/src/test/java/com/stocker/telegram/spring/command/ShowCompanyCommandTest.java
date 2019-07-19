package com.stocker.telegram.spring.command;

import com.stocker.telegram.data.Company;
import com.stocker.telegram.exception.NoSymbolException;
import com.stocker.telegram.spring.CompanyDataClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowCompanyCommandTest {

    @InjectMocks
    private ShowCompanyCommand showCompanyCommand;

    @Mock
    private CompanyDataClient companyDataClient;

    @Mock
    private Function<SendMessage, SendMessage> callback;

    @Before
    public void init() {
        Mockito.when(companyDataClient.getCompany("AAPL")).thenReturn(Mono.just(new Company("Apple Inc", "aapl")));
    }

    @Test
    public void correctExtractSymbol() throws NoSymbolException {
        assertEquals("T is correct symbol here", "T", ShowCompanyCommand.getSymbol("show T"));
        assertEquals("t is correct symbol here, it should be converted to uppercase","T", ShowCompanyCommand.getSymbol("show t"));
        assertEquals("T is correct symbol here, third word should break extraction","T", ShowCompanyCommand.getSymbol("show T notT"));
        assertEquals("T is correct symbol here, third word should break extraction","T", ShowCompanyCommand.getSymbol("show T notT"));
        assertEquals("T is correct symbol here, two spaces is possible","T", ShowCompanyCommand.getSymbol("show  T"));
        assertEquals("T is correct symbol here, started space should brake logic","T", ShowCompanyCommand.getSymbol(" show T"));
        assertEquals("T is correct symbol here, _ should be splitter","T", ShowCompanyCommand.getSymbol(" show_T"));
    }

    @Test(expected = NoSymbolException.class)
    public void notSymbolOnlySpace() throws NoSymbolException {
        ShowCompanyCommand.getSymbol("show ");
    }

    @Test(expected = NoSymbolException.class)
    public void notSymbol() throws NoSymbolException {
        ShowCompanyCommand.getSymbol("show");
    }

    @Test
    public void processSuccessFound() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("show aapl");
        showCompanyCommand.process(update, callback);
//        Mockito.verify(callback);
    }

}