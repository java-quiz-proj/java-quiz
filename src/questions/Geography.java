package questions;

import java.util.List;

public class Geography extends Questions implements Category {
    public Geography() {
        addQuestion("Która z tych rzek płynie przez Brazylię?", "Amazonka", 
            "Amazonka", "Nil", "Ganges", "Mississippi");

        addQuestion("Który ocean jest największy?", "Spokojny", 
            "Spokojny", "Atlantyk", "Indyjski", "Arktyczny");

        addQuestion("Jak długi jest Równik?", "~40k km", 
            "~40k km", "~120k km", "~77k km", "~25k km");

        addQuestion("Jaki jest drugi największy kraj pod względem terytorium?", "Kanada", 
            "Kanada", "Rosja", "Brazylia", "Chiny");

        addQuestion("Co jest stolicą Australii?", "Canberra", 
            "Canberra", "Melbourne", "Sydney", "żadna z tych");
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
