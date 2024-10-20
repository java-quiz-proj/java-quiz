import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportHandler {
    private FileWriter reportWriter;

    public ReportHandler(String date) {
        try {
            // tworzenie pliku
            File reportFile = new File("report_" + date + ".txt");
            if (reportFile.createNewFile()) {
                System.out.println("File created: " + reportFile.getName());
            } else {
                System.out.println("File already exists.");
            }

            // tworzenie strumienia zapisu
            reportWriter = new FileWriter("report_" + date + ".txt");
            System.out.println("FileWriter created: " + reportFile.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeMessage(String msg) {
        try {
            reportWriter.write(msg);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}