package com.stocker.telegram;

import com.stocker.telegram.StockTelegramConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TelegramComponentIT {

    @Ignore
    @Test
    public void connectionTest() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(StockTelegramConfiguration.class);
    }
}
