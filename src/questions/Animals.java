package questions;

import java.util.List;

public class Animals extends Question implements Category {
    public Animals() {
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
