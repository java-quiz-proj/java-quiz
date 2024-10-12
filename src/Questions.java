import java.util.ArrayList;
import java.util.List;

public class Questions {
    private List<String> questions = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> answers1 = new ArrayList<>();
    private List<String> answers2 = new ArrayList<>();
    private List<String> answers3 = new ArrayList<>();
    private List<String> answers4 = new ArrayList<>();

    public Questions(){

        questions.add("Jakie miasto jest stolicą Polski?");
        correctAnswers.add("Warszawa");
        answers1.add("Białystok");
        answers2.add("Kraków");
        answers3.add("Kielce");
        answers4.add("Warszawa");

        questions.add("Które z tych drzew zrzuca igły na zimę?");
        correctAnswers.add("Modrzew");
        answers1.add("Sosna");
        answers2.add("Modrzew");
        answers3.add("Jodła");
        answers4.add("Tuja");

        questions.add("W którym roku Kolumb odkrył Amerykę?");
        correctAnswers.add("1492");
        answers1.add("1512");
        answers2.add("1603");
        answers3.add("1492");
        answers4.add("1381");

    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public String getQuestion(int n) {
        return questions.get(n);
    }

    public List<String> getAnswers1() {
        return answers1;
    }

    public List<String> getAnswers2() {
        return answers2;
    }

    public List<String> getAnswers3() {
        return answers3;
    }

    public List<String> getAnswers4() {
        return answers4;
    }
}
