package questions;

import java.util.List;

public class Maths extends Question implements Category {
    public Maths(){
        loadQuestions("maths_questions.txt"); // Upewnij się, że plik jest w odpowiednim miejscu
    }

    public List<String> getQuestions() {
        return questions; // Implementacja metody getQuestions
    }

    @Override
    public String getQuestion(int n) {
        return questions.get(n); // Uzyskiwanie pytania z listy
    }

    @Override
    public List<String> getAnswers(int n) {
        return answers.get(n); // Uzyskiwanie odpowiedzi 1
    }

    @Override
    public List<String> getCorrectAnswers() {
        return correctAnswers; // Implementacja metody getCorrectAnswers
    }
}


