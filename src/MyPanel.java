import questions.*;  // Importuje wszystkie klasy z pakietu questions

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;

public class MyPanel extends JPanel {
    private int score;  // Zmienna przechowująca wynik gracza
    private JLabel questionLabel, scoreLabel, endScoreLabel;  // Etykiety do wyświetlania pytania, wyniku oraz końcowego wyniku
    private JButton buttonA, buttonB, buttonC, buttonD, chooseCategoryButton;  // Przyciski do wyboru odpowiedzi oraz powrotu do wyboru kategorii

    private List<Integer> shuffledQuestionsIndices = new ArrayList<>();  // Lista z indeksami pytań, które zostaną losowo przetasowane
    private int currentQuestionIndex;  // Numer aktualnego pytania
    private Questions currentCategoryQuestions;  // Obiekt zawierający pytania z wybranej kategorii

    public MyPanel(String category) {
        score = 0;  // Inicjalizacja wyniku na 0
        questionLabel = new JLabel("" + currentQuestionIndex);  // Etykieta pytania (początkowo pusta)
        scoreLabel = new JLabel("Wynik: " + score);  // Etykieta z wynikiem
        buttonA = new JButton("");  // Inicjalizacja przycisków do odpowiedzi
        buttonB = new JButton("");
        buttonC = new JButton("");
        buttonD = new JButton("");

        currentQuestionIndex = 0;  // Ustawienie początkowego pytania na pierwsze

        // Wybór kategorii pytań w zależności od podanego argumentu (polimorfizm)
        switch (category) {
            case "maths" -> currentCategoryQuestions = new Maths();
            case "geography" -> currentCategoryQuestions = new Geography();
            case "animals" -> currentCategoryQuestions = new Animals();
            case "history" -> currentCategoryQuestions = new History();
        }

        // Dodanie indeksów pytań do listy i ich przetasowanie
        for (int i = 0; i < currentCategoryQuestions.getQuestions().size(); i++) {
            shuffledQuestionsIndices.add(i);
        }
        Collections.shuffle(shuffledQuestionsIndices);  // Tasowanie pytań

        // Dodanie ActionListenerów do przycisków odpowiedzi
        buttonA.addActionListener(e -> {
            handleClick(currentCategoryQuestions, buttonA.getText(), "A");
        });

        buttonB.addActionListener(e -> {
            handleClick(currentCategoryQuestions, buttonB.getText(), "B");
        });

        buttonC.addActionListener(e -> {
            handleClick(currentCategoryQuestions, buttonC.getText(), "C");
        });

        buttonD.addActionListener(e -> {
            handleClick(currentCategoryQuestions, buttonD.getText(), "D");
        });

        // Dodanie elementów do panelu i ustawienie układu siatki
        add(questionLabel);
        add(scoreLabel);
        setLayout(new GridLayout(3, 2));  // Ustawienie GridLayout dla przycisków i etykiet
        add(buttonA);
        add(buttonB);
        add(buttonC);
        add(buttonD);

        // Wywołanie metody do załadowania pierwszego pytania
        loadNewQuestion(currentCategoryQuestions);
    }

    // Obsługa kliknięcia przycisku odpowiedzi
    private void handleClick(Questions questions, String answer, String buttonId) {
        Timer timer = new Timer();  // Tworzenie nowego timera, aby dodać opóźnienie między pytaniami
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // Sprawdzenie, czy nie skończyły się pytania
                if (currentQuestionIndex == questions.getQuestions().size()) {
                    endQuiz();  // Zakończ grę, jeśli wszystkie pytania zostały wyświetlone
                }

                // Wczytanie nowego pytania, jeśli są jeszcze dostępne
                if (currentQuestionIndex < questions.getQuestions().size()) {
                    loadNewQuestion(questions);
                } else {
                    questionLabel.setText("Koniec pytań");  // Wyświetl informację o zakończeniu pytań
                }

                // Resetowanie kolorów i włączenie przycisków
                buttonA.setBackground(null);
                buttonB.setBackground(null);
                buttonC.setBackground(null);
                buttonD.setBackground(null);

                buttonA.setEnabled(true);
                buttonB.setEnabled(true);
                buttonC.setEnabled(true);
                buttonD.setEnabled(true);
            }
        }, 2000);  // Opóźnienie 2 sekundy

        // Sprawdzenie poprawności odpowiedzi
        checkAnswer(questions, answer, buttonId);
    }

    // Metoda do zakończenia quizu
    private void endQuiz() {
        chooseCategoryButton = new JButton("Wybierz kategorię");  // Przycisk do wyboru nowej kategorii
        endScoreLabel = new JLabel("Gratulacje, zdobywasz " + score + " punktów!");  // Wyświetlenie końcowego wyniku
        setLayout(new GridLayout(8, 1));  // Zmiana układu na więcej wierszy
        add(endScoreLabel, 0);
        add(chooseCategoryButton, 1);

        // Ukrycie elementów związanych z pytaniami
        questionLabel.setVisible(false);
        scoreLabel.setVisible(false);
        buttonA.setVisible(false);
        buttonB.setVisible(false);
        buttonC.setVisible(false);
        buttonD.setVisible(false);

        // Obsługa wyboru nowej kategorii po zakończeniu quizu
        chooseCategoryButton.addActionListener(e -> {
            remove(questionLabel);
            remove(scoreLabel);
            remove(buttonA);
            remove(buttonB);
            remove(buttonC);
            remove(buttonD);
            remove(chooseCategoryButton);
            remove(endScoreLabel);
            Category.chooseCat();  // Wywołanie metody do wyboru kategorii (prawdopodobnie z innej klasy)
        });
    }

    // Metoda do załadowania nowego pytania
    private void loadNewQuestion(Questions questions) {
        // Pobieranie odpowiedzi i mieszanie ich kolejności
        List<String> answers = new ArrayList<>();
        answers.add(questions.getAnswers1().get(shuffledQuestionsIndices.get(currentQuestionIndex)));
        answers.add(questions.getAnswers2().get(shuffledQuestionsIndices.get(currentQuestionIndex)));
        answers.add(questions.getAnswers3().get(shuffledQuestionsIndices.get(currentQuestionIndex)));
        answers.add(questions.getAnswers4().get(shuffledQuestionsIndices.get(currentQuestionIndex)));

        Collections.shuffle(answers);  // Przetasowanie odpowiedzi

        // Ustawienie nowego pytania i odpowiedzi na przyciskach
        questionLabel.setText("" + (currentQuestionIndex + 1) + ". " + questions.getQuestion(shuffledQuestionsIndices.get(currentQuestionIndex)));
        buttonA.setText(answers.get(0));
        buttonB.setText(answers.get(1));
        buttonC.setText(answers.get(2));
        buttonD.setText(answers.get(3));

        currentQuestionIndex++;  // Zwiększenie licznika pytań
    }

    // Metoda sprawdzająca poprawność odpowiedzi
    private void checkAnswer(Questions questions, String answer, String buttonId) {
        // Wyłączenie przycisków po wyborze odpowiedzi
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

        // Jeśli to nie jest pierwsze pytanie
        if (currentQuestionIndex != 0) {
            // Sprawdzenie, czy odpowiedź jest poprawna
            if (answer.equals(questions.getCorrectAnswers().get(shuffledQuestionsIndices.get(currentQuestionIndex - 1)))) {
                score++;  // Zwiększenie wyniku
                scoreLabel.setText("Wynik: " + score);  // Aktualizacja wyniku

                // Zmiana koloru odpowiedniego przycisku na zielony, jeśli odpowiedź jest poprawna
                switch (buttonId) {
                    case "A" -> buttonA.setBackground(new Color(0, 255, 0));
                    case "B" -> buttonB.setBackground(new Color(0, 255, 0));
                    case "C" -> buttonC.setBackground(new Color(0, 255, 0));
                    case "D" -> buttonD.setBackground(new Color(0, 255, 0));
                }
            } else {
                // Jeśli odpowiedź jest błędna, zaznacz poprawną odpowiedź na zielono
                if (buttonA.getText().equals(questions.getCorrectAnswers().get(shuffledQuestionsIndices.get(currentQuestionIndex - 1))))
                    buttonA.setBackground(new Color(0, 255, 0));
                if (buttonB.getText().equals(questions.getCorrectAnswers().get(shuffledQuestionsIndices.get(currentQuestionIndex - 1))))
                    buttonB.setBackground(new Color(0, 255, 0));
                if (buttonC.getText().equals(questions.getCorrectAnswers().get(shuffledQuestionsIndices.get(currentQuestionIndex - 1))))
                    buttonC.setBackground(new Color(0, 255, 0));
                if (buttonD.getText().equals(questions.getCorrectAnswers().get(shuffledQuestionsIndices.get(currentQuestionIndex - 1))))
                    buttonD.setBackground(new Color(0, 255, 0));
                // Zmiana koloru przycisku na czerwony, jeśli odpowiedź jest błędna
                switch (buttonId) {
                    case "A" -> buttonA.setBackground(new Color(255, 0, 0));
                    case "B" -> buttonB.setBackground(new Color(255, 0, 0));
                    case "C" -> buttonC.setBackground(new Color(255, 0, 0));
                    case "D" -> buttonD.setBackground(new Color(255, 0, 0));
                }
            }
        }
    }
}