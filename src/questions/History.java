package questions;

import java.util.List;

public class History extends Question implements Category {
    public History() {
        addQuestion("Kiedy pierwszy człowiek wylądował na Księżycu?", "20 lipca 1969", 
            "20 lipca 1969", "19 lipca 1971", "21 lipca 1968", "21 lipca 1970");

        addQuestion("Co wynalazł Tomas Edison?", "żarówkę", 
            "żarówkę", "iPada", "wi-fi", "słuchawki");

        addQuestion("Kto był pierwszym cesarzem Rzymu?", "Oktawian August", 
            "Oktawian August", "Juliusz Cezar", "Neron", "Romulus");

        addQuestion("Która cywilizacja wynalazła pismo?", "Sumerowie", 
            "Sumerowie", "starożytni Egipcjanie", "starożytni Grecy", "starożytni Chińczycy");

        addQuestion("Kto był pierwszym królem Polski?", "Bolesław Chrobry", 
            "Bolesław Chrobry", "Mieszko I", "Mieszko II", "Władysław Jagiełło");
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
