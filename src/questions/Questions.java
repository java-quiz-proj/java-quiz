package questions;

import java.util.ArrayList;
import java.util.List;

public abstract class Questions {
    protected List<String> questions;
    protected List<String> correctAnswers;
    protected List<List<String>> answers;

    public Questions() {
        questions = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        answers = new ArrayList<>();
    }

    // Method to add questions in the class
    protected void addQuestion(String question, String correctAnswer, String... answersList) {
        questions.add(question);
        correctAnswers.add(correctAnswer);

        List<String> currAnswers = new ArrayList<>();
        for (String answer : answersList) {
            currAnswers.add(answer);
        }
        answers.add(currAnswers);
    }
}
