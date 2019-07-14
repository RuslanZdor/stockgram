package com.stocker.telegram.spring.command;

import com.stocker.telegram.exception.NoSymbolException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShowCompanyCommandTest {

    @Test
    public void correctExtractSymbol() throws NoSymbolException {
        assertEquals("T is correct symbol here", "T", ShowCompanyCommand.getSymbol("show T"));
        assertEquals("t is correct symbol here, it should be converted to uppercase","T", ShowCompanyCommand.getSymbol("show t"));
        assertEquals("T is correct symbol here, third word should break extraction","T", ShowCompanyCommand.getSymbol("show T notT"));
        assertEquals("T is correct symbol here, third word should break extraction","T", ShowCompanyCommand.getSymbol("show T notT"));
        assertEquals("T is correct symbol here, two spaces is possible","T", ShowCompanyCommand.getSymbol("show  T"));
        assertEquals("T is correct symbol here, started space should brake logic","T", ShowCompanyCommand.getSymbol(" show T"));
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