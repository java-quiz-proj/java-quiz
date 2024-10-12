package questions;

import java.util.ArrayList;
import java.util.List;

public class Geography implements Questions {


    private List<String> questions = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> answers1 = new ArrayList<>();
    private List<String> answers2 = new ArrayList<>();
    private List<String> answers3 = new ArrayList<>();
    private List<String> answers4 = new ArrayList<>();

    public Geography(){

        questions.add("Któa z tych rzek płynie przez Brazylię?");
        correctAnswers.add("Amazonka");
        answers1.add("Amazonka");
        answers2.add("Nil");
        answers3.add("Ganges");
        answers4.add("Mississippi");

        questions.add("Który ocean jest największy?");
        correctAnswers.add("Spokojny");
        answers1.add("Spokojny");
        answers2.add("Atlantyk");
        answers3.add("Indyjski");
        answers4.add("Arktyczny");

        questions.add("Jak długi jest Równik?");
        correctAnswers.add("~40k km");
        answers1.add("~120k km");
        answers2.add("~40k km");
        answers3.add("~77k km");
        answers4.add("~25k km");

        questions.add("Jaki jest drugi największy kraj pod względem terytorium?");
        correctAnswers.add("Kanada");
        answers1.add("Kanada");
        answers2.add("Rosja");
        answers3.add("Brazylia");
        answers4.add("Chiny");

        questions.add("Co jest stolicą Australii??");
        correctAnswers.add("Canberra");
        answers1.add("Canberra");
        answers2.add("Melbourne");
        answers3.add("Sydney");
        answers4.add("żadna z tych");
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


