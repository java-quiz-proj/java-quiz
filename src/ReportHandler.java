import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ReportHandler {
    private static Logger logger;

    static {
        try {
            // Utwórz nową instancję logger
            logger = Logger.getLogger("MyLog");

            // Utwórz unikową nazwę pliku na podstawie obecnej daty i czasu
            String timestamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
            String logFileName = "report_" + timestamp + ".txt";

            // Ustaw FileHandler by pisać do pliku
            FileHandler fh = new FileHandler(logFileName);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}