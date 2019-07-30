package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.NoSymbolException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ShowCompanyCommandTest {

    @Before
    public void init() {
        Company company = new Company();
        company.setName("Apple Inc");
        company.setSymbol("aapl");
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

}