import questions.*;  // Importuje wszystkie klasy z pakietu questions
import report.*;

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
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class MyPanel extends JPanel {
    private final int defaultAnswerAmount = 4;
    private int score;  // Zmienna przechowująca wynik gracza
    private JLabel questionLabel, scoreLabel, endScoreLabel;  // Etykiety do wyświetlania pytania, wyniku oraz końcowego wyniku
    private JButton chooseCategoryButton;  // Przyciski do wyboru odpowiedzi oraz powrotu do wyboru kategorii
    private List<JButton> answerButtons;

    private List<Integer> shuffledQuestionsIndices = new ArrayList<>();  // Lista z indeksami pytań, które zostaną losowo przetasowane
    private int currentQuestionIndex;  // Numer aktualnego pytania
    private Category currentCategoryQuestions;  // Obiekt zawierający pytania z wybranej kategorii

    public MyPanel(String category) {
        score = 0;  // Inicjalizacja wyniku na 0
        questionLabel = new JLabel("" + currentQuestionIndex);  // Etykieta pytania (początkowo pusta)
        scoreLabel = new JLabel("Wynik: " + score);  // Etykieta z wynikiem
        answerButtons = new ArrayList<>(); // Lista zawierająca przyciski do odpowiedzi
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
        
        // Dodanie elementów do panelu i ustawienie układu siatki
        add(questionLabel);
        add(scoreLabel);

        for (int i = 0; i < defaultAnswerAmount; i++) {
            JButton button = new JButton("");
            answerButtons.add(button);
            add(button);
        }
        setLayout(new GridLayout(6, 1, 0, 15));  // Ustawienie GridLayout dla przycisków i etykiet
        // Wywołanie metody do załadowania pierwszego pytania
        loadNewQuestion(currentCategoryQuestions);
    }

    // Obsługa kliknięcia przycisku odpowiedzi
    private void handleClick(Category questions, String answer, Integer buttonId) {
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
                for (JButton button : answerButtons) {
                    button.setBackground(null);
                    button.setEnabled(true);
                }
            }
        }, 2000);  // Opóźnienie 2 sekundy

        // Sprawdzenie poprawności odpowiedzi
        checkAnswer(questions, answer, buttonId);
    }

    // Metoda do zakończenia quizu
    private void endQuiz() {
        chooseCategoryButton = new JButton("Wybierz kategorię");  // Przycisk do wyboru nowej kategorii
        endScoreLabel = new JLabel("Gratulacje, zdobywasz " + score + " punktów!");  // Wyświetlenie końcowego wyniku
        setLayout(new GridLayout(12, 1));  // Zmiana układu na więcej wierszy
        add(endScoreLabel, 0);
        add(chooseCategoryButton, 1);
        // Ukrycie elementów związanych z pytaniami
        questionLabel.setVisible(false);
        scoreLabel.setVisible(false);
        for (JButton button : answerButtons) {
            button.setVisible(false);
        }

        // Obsługa wyboru nowej kategorii po zakończeniu quizu
        chooseCategoryButton.addActionListener(e -> {
            remove(questionLabel);
            remove(scoreLabel);
            for (JButton button : answerButtons) {
                remove(button);
            }
            remove(chooseCategoryButton);
            remove(endScoreLabel);
            CategoryView.chooseCat();  // Wywołanie metody do wyboru kategorii (prawdopodobnie z innej klasy)
        });
    }

    // Metoda do załadowania nowego pytania
    private void loadNewQuestion(Category questions) {
        if (shuffledQuestionsIndices.isEmpty() || currentQuestionIndex >= shuffledQuestionsIndices.size()) {
            questionLabel.setText("Brak pytań do wyświetlenia.");
            return;
        }
        
        // Pobieranie odpowiedzi i mieszanie ich kolejności
        List<String> answers = new ArrayList<>();
        answers = questions.getAnswers(shuffledQuestionsIndices.get(currentQuestionIndex));
        Collections.shuffle(answers);  // Przetasowanie odpowiedzi
        Logger logger = ReportHandler.getLogger();
        logger.info(currentQuestionIndex + " " + answers);


        // Ustawienie nowego pytania i odpowiedzi na przyciskach
        questionLabel.setText("" + (currentQuestionIndex + 1) + ". " + questions.getQuestion(shuffledQuestionsIndices.get(currentQuestionIndex)));

        // Inicjalizacja przycisków do odpowiedzi
        Integer answerAmount = questions.getAnswers(shuffledQuestionsIndices.get(currentQuestionIndex)).size();
        for (int i = 0; i < defaultAnswerAmount; i++) {
            JButton newButton = answerButtons.get(i);
            // Gdy jest mniej odpowiedzi niż domyślnie
            if (i > answerAmount-1) {
                newButton.setVisible(false);
            } else {
                if (!newButton.isVisible()) { // Jeżeli został wcześniej zmieniony na niewidzialny
                    newButton.setVisible(true);
                }
                newButton.setText(answers.get(i));
                
                // Usunięcie poprzednich ActionListenerów
                for (ActionListener al : newButton.getActionListeners()) {
                    newButton.removeActionListener(al);
                }
                // Dodanie ActionListenerów do przycisków odpowiedzi
                final Integer buttonID = i;
                newButton.addActionListener(e -> {
                    logger.info("Button " + (buttonID + 1) + " clicked!");
                    handleClick(currentCategoryQuestions, newButton.getText(), buttonID);
                });
            }
        }

        currentQuestionIndex++;  // Zwiększenie licznika pytań
    }


    // Metoda sprawdzająca poprawność odpowiedzi
    private void checkAnswer(Category questions, String answer, Integer buttonId) {
        // Wyłączenie przycisków po wyborze odpowiedzi
        for (JButton button : answerButtons) {
            button.setEnabled(false);
        }

        // Jeśli to nie jest pierwsze pytanie
        if (currentQuestionIndex != 0) {
            final int index = shuffledQuestionsIndices.get(currentQuestionIndex - 1);
            // Sprawdzenie, czy odpowiedź jest poprawna
            if (answer.equals(questions.getCorrectAnswers().get(index))) {
                score++;  // Zwiększenie wyniku
                scoreLabel.setText("Wynik: " + score);  // Aktualizacja wyniku

                // Zmiana koloru odpowiedniego przycisku na zielony, jeśli odpowiedź jest poprawna
                answerButtons.get(buttonId).setBackground(new Color(0, 255, 0));
            } else {
                // Jeśli odpowiedź jest błędna, zaznacz poprawną odpowiedź na zielono
                for (int i = 0; i < questions.getAnswers(index).size(); i++) {
                    JButton button = answerButtons.get(i);
                    if (button.getText().equals(questions.getCorrectAnswers().get(index))) {
                        button.setBackground(new Color(0, 255, 0));
                    }
                }

                // Zmiana koloru przycisku na czerwony, jeśli odpowiedź jest błędna
                answerButtons.get(buttonId).setBackground(new Color(255, 0, 0));
            }
        }
    }
}