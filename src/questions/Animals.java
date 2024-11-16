package questions;

import java.util.List;

public class Animals extends Questions implements Category {
    public Animals() {
        addQuestion("Ile kręgów szyjnych ma żyrafa?", "7",
            "7", "19", "12");

        addQuestion("Ile odnóży mają homary?", "10",
            "10", "8", "12", "6");

        addQuestion("Co NIE jest prawdą o dziobaku?", "potrafi latać",
            "potrafi latać", "znosi jaja", "produkuje truciznę", "posiada dziób");

        addQuestion("Jaki kolor ma skóra niedźwiedzia polarnego?", "czarny",
             "czarny", "różowy", "biały");

        addQuestion("Jakie jest największe żyjące zwierzę?", "płetwal błękitny",
            "płetwal błękitny", "żyrafa", "słoń", "rekin olbrzymi");
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
