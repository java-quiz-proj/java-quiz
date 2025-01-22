package questions;

import java.util.List;

public class Animals extends Questions implements Category {

   {
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
        return questions; // Implement getQuestions method
    }

    @Override
    public String getQuestion(int n) {
        return questions.get(n); // Get n-th question from the list
    }

    @Override
    public List<String> getAnswers(int n) {
        return answers.get(n); // Get answers to n-th question
    }

    @Override
    public List<String> getCorrectAnswers() {
        return correctAnswers; // Implement getCorrectAnswers method
    }
}
