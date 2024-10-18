package questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Question {
    protected List<String> questions;
    protected List<String> correctAnswers;
    protected List<List<String>> answers;

    public Question() {
        questions = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        answers = new ArrayList<>();
    }

    // Metoda do ładowania pytań z pliku
    protected void loadQuestions(String filePath) {
        QuestionLoader loader = new QuestionLoader();
        loader.load(filePath);
    }

    protected void addQuestion(String question, String correctAnswer, String... answersList) {
        questions.add(question);
        correctAnswers.add(correctAnswer);

        List<String> currAnswers = new ArrayList<>();
        for (String answer : answersList) {
            currAnswers.add(answer);
        }
        answers.add(currAnswers);
    }

    // Klasa wewnętrzna do ładowania pytań
    private class QuestionLoader {
        // Główna metoda do ładowania pytań
        public void load(String filePath) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    processLine(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Metoda do przetwarzania pojedynczej linii
        private void processLine(String line) {
            String[] parts = line.split("\\|");
            if (isLineValid(parts)) {
                addQuestion(parts);
            } else {
                System.err.println("Niepoprawny format pytania: " + line);
            }
        }

        // Metoda do walidacji linii
        private boolean isLineValid(String[] parts) {
            return parts.length >= 3; // Upewnij się, że długość tablicy jest odpowiednia
        }

        // Metoda do dodawania pytania i odpowiedzi do list
        private void addQuestion(String[] parts) {
            questions.add(parts[0]); // Pytanie
            correctAnswers.add(parts[1]); // Poprawna odpowiedź
            System.out.println("Pobrano pytanie: " + parts[0]);
            System.out.println("Poprawna odpowiedź: " + parts[1]);

            // Dodaj wszystkie odpowiedzi (parts[2], .. parts[5])
            List<String> currAnswers = new ArrayList<>();
            for (int i = 2; i < parts.length; i++) {
                currAnswers.add(parts[i]);
                System.out.println("Odpowiedź " + (i-1) + ": " + parts[i]);
            }
            answers.add(currAnswers);
        }
    }
}
