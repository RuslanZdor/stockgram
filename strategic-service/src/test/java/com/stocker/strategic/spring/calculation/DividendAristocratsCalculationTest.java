package com.stocker.strategic.spring.calculation;

import com.stocker.strategic.StockStrategicConfigurationForTest;
import com.stocker.yahoo.data.Company;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StockStrategicConfigurationForTest.class})
public class DividendAristocratsCalculationTest {

    @Autowired
    private DividendAristocratsCalculation dividendAristocratsCalculation;

    @Test
    public void calculate() {
        List<Company> list = dividendAristocratsCalculation.calculate();
//        assertFalse(list.isEmpty());
//        assertTrue(list.get(0).getSymbol().equalsIgnoreCase("T"));
    }
}