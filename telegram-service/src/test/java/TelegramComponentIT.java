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

//        StockTelegramComponent stockTelegramComponent = context.getBean("telegramComponent", StockTelegramComponent.class);

//        System.out.print(stockTelegramComponent);

    }
}
