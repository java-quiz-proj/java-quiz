package questions;

import java.util.ArrayList;
import java.util.List;

public class History implements Category {

    private List<String> questions = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> answers1 = new ArrayList<>();
    private List<String> answers2 = new ArrayList<>();
    private List<String> answers3 = new ArrayList<>();
    private List<String> answers4 = new ArrayList<>();

    public History(){
        questions.add("Kiedy pierwszy człowiek wylądował na Księżycu");
        correctAnswers.add("20 lipca 1969");
        answers1.add("20 lipca 1969");
        answers2.add("19 lipca 1971");
        answers3.add("21 lipca 1968");
        answers4.add("21 lipca 1970");

        questions.add("Co wynalazł Tomas Edison?");
        correctAnswers.add("żarówkę");
        answers1.add("żarówkę");
        answers2.add("iPada");
        answers3.add("wi-fi");
        answers4.add("słuchawki");

        questions.add("Kto był pierwszym cesarzem Rzymu?");
        correctAnswers.add("Oktawian August");
        answers1.add("Oktawian August");
        answers2.add("Juliusz Cezar");
        answers3.add("Neron");
        answers4.add("Romulus");

        questions.add("Która cywilizacja wynalazła pismo?");
        correctAnswers.add("Sumerowie");
        answers1.add("Sumerowie");
        answers2.add("starożytni Egipcjanie");
        answers3.add("starożytni Grecy");
        answers4.add("starożytni Chińczycy");

        questions.add("Kto był pierwszym królem Polski?");
        correctAnswers.add("Bolesław Chrobry");
        answers1.add("Bolesław Chrobry");
        answers2.add("Mieszko I");
        answers3.add("Mieszko II");
        answers4.add("Władysław Jagiełło");
    }

    @Override
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }
    @Override
    public List<String> getQuestions() {
        return questions;
    }
    @Override
    public String getQuestion(int n) {
        return questions.get(n);
    }
    @Override
    public List<String> getAnswers1() {
        return answers1;
    }
    @Override
    public List<String> getAnswers2() {
        return answers2;
    }
    @Override
    public List<String> getAnswers3() {
        return answers3;
    }
    @Override
    public List<String> getAnswers4() {
        return answers4;
    }
}
