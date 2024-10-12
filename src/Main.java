
import javax.swing.JFrame;
public class Main{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quiz");
        MyPanel panel = new MyPanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

//TODO: przycisk nowej gry, dodać drugi tryb, może pytania i odpowiedzi brać z pliku .txt,
// dodać losowość wyświetlania pytań, zapis wyników


