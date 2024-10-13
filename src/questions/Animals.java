package questions;

import java.util.List;

public class Animals extends Question implements Category {
    public Animals() {
        loadQuestions("animals_questions.txt"); // Upewnij się, że plik jest w odpowiednim miejscu
    }

    @Override
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
