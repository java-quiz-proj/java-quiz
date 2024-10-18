package questions;

public class Maths extends BaseCategory {
    public Maths(){

        addQuestion("sin(90 deg) = ?", "1", 
            "1", "~ -1,7", "0", "~ 1,5");
        
        addQuestion("Jaka jest najmniejsza liczba pierwsza?", "2", 
            "2", "3", "5", "1");
        
        addQuestion("Która z tych liczb ma najwięcej dzielników?", "12", 
            "12", "20", "10", "15");
        
        addQuestion("Które z tych przybliżeń pi jest najdokładniejsze?", "3,14159", 
            "3,14159", "3.14253", "3,14227", "3,14149");
        
        addQuestion("Jak wygląda wzór na pole koła?", "pi*r*r", 
            "2*pi*r", "r*r", "pi*r*r", "a*h/2");

    }
}
