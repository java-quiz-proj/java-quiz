package questions;

import java.util.ArrayList;
import java.util.List;

public class Animals implements Questions{
    private List<String> questions = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> answers1 = new ArrayList<>();
    private List<String> answers2 = new ArrayList<>();
    private List<String> answers3 = new ArrayList<>();
    private List<String> answers4 = new ArrayList<>();

    public Animals(){
        questions.add("ile kręgów szyjnych ma żyrafa");
        correctAnswers.add("7");
        answers1.add("7");
        answers2.add("19");
        answers3.add("12");
        answers4.add("21");

        questions.add("Ile odnóży mają homary");
        correctAnswers.add("10");
        answers1.add("10");
        answers2.add("8");
        answers3.add("12");
        answers4.add("6");

        questions.add("Co NIE jest prawdą o dziobaku?");
        correctAnswers.add("potrafi latać");
        answers1.add("potrafi latać");
        answers2.add("znosi jaja");
        answers3.add("produkuje truciznę");
        answers4.add("posiada dziób");

        questions.add("Jaki kolor ma skóra niedźwiedzia polarnego?");
        correctAnswers.add("czarny");
        answers1.add("czarny");
        answers2.add("różowy");
        answers3.add("biały");
        answers4.add("żółty");

        questions.add("Jakie jest największe żyjące zwierzę?");
        correctAnswers.add("płetwal błękitny");
        answers1.add("płetwal błękitny");
        answers2.add("żyrafa");
        answers3.add("słoń");
        answers4.add("rekin olbrzymi");
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
