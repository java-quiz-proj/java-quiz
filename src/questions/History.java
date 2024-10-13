package questions;

import java.util.List;

public class History extends Question implements Category {
    public History(){
        loadQuestions("history_questions.txt"); // Upewnij się, że plik jest w odpowiednim miejscu
    }

    public List<String> getQuestions() {
        return questions; // Implementacja metody getQuestions
    }

    @Override
    public String getQuestion(int n) {
        return questions.get(n); // Uzyskiwanie pytania z listy
    }

    @Override
    public List<String> getAnswers1() {
        return answers1; // Uzyskiwanie odpowiedzi 1
    }

    @Override
    public List<String> getAnswers2() {
        return answers2; // Uzyskiwanie odpowiedzi 2
    }

    @Override
    public List<String> getAnswers3() {
        return answers3; // Uzyskiwanie odpowiedzi 3
    }

    @Override
    public List<String> getAnswers4() {
        return answers4; // Uzyskiwanie odpowiedzi 4
    }

    @Override
    public List<String> getCorrectAnswers() {
        return correctAnswers; // Implementacja metody getCorrectAnswers
    }
}
