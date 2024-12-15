package views;

import questions.*;  // Importuje wszystkie klasy z pakietu questions
import utils.*;
import models.*;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class QuizView extends JPanel {
    private final int defaultAnswerAmount = 4;
    private int score;  // Variable storing player's resuly
    private JLabel questionLabel, scoreLabel, endScoreLabel;  // Labels for questions, current score and end score
    private JButton goToMenuButton;  // Buttons for choosing answer and return to menu
    private List<JButton> answerButtons;

    private List<Integer> shuffledQuestionsIndices = new ArrayList<>();  // List with questions' indexes that will get shuffled
    private int currentQuestionIndex;  // Current question number
    private Category currentCategoryQuestions;  // Object with the current category's questions
    private String currentCategory;

    private static final Logger logger = ReportHandler.getLogger();

    public QuizView(String category) {
        currentCategory = category; // Store current category
        score = 0;  // Initialize score to 0
        questionLabel = new JLabel("" + currentQuestionIndex);  // Question label (empty at the start)
        scoreLabel = new JLabel("Wynik: " + score);  // Score label
        answerButtons = new ArrayList<>(); // List with buttons containing answers
        currentQuestionIndex = 0;  // Set starting question to 0

        // Choose the category of questions depending on the argument (polymorphism)
        logger.info("Initializing quiz for category: " + category);
        try {
            switch (category) {
                case "Maths" -> currentCategoryQuestions = new Maths();
                case "Geography" -> currentCategoryQuestions = new Geography();
                case "Animals" -> currentCategoryQuestions = new Animals();
                case "History" -> currentCategoryQuestions = new History();
                default -> throw new IllegalArgumentException("Invalid category: " + category);
            }
        } catch (NullPointerException e) {
            logger.severe("Category is null: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            logger.severe("Invalid category: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.severe("Error initializing category: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Add question indexes to list and shuffle them
        try {
            for (int i = 0; i < currentCategoryQuestions.getQuestions().size(); i++) {
                shuffledQuestionsIndices.add(i);
            }
            Collections.shuffle(shuffledQuestionsIndices);
        } catch (Exception e) {
            logger.severe("Error shuffling questions: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Add elements to panel and set grid layout
        add(questionLabel);
        add(scoreLabel);
        logger.info("QuizView added to frame");

        for (int i = 0; i < defaultAnswerAmount; i++) {
            JButton button = new JButton("");
            answerButtons.add(button);
            add(button);
        }
        setLayout(new GridLayout(6, 1, 0, 15));  // Set GridLayout for buttons and labels
        // Call method to load first question
        loadNewQuestion(currentCategoryQuestions);
    }

    // Obsługa kliknięcia przycisku odpowiedzi
    private void handleClick(Category questions, String answer, Integer buttonId) {
        Timer timer = new Timer();  // Tworzenie nowego timera, aby dodać opóźnienie między pytaniami
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // Sprawdzenie, czy nie skończyły się pytania
                    if (currentQuestionIndex == questions.getQuestions().size()) {
                        endQuiz();  // Zakończ grę, jeśli wszystkie pytania zostały wyświetlone
                        logger.info("Quiz ended, all questions answered.");
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
        } catch (Exception e) {
            logger.severe("Error scheduling timer: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Sprawdzenie poprawności odpowiedzi
        checkAnswer(questions, answer, buttonId);
    }

    // Method for ending the quiz
    private void endQuiz() {
        try {
            goToMenuButton = new JButton("Wróć do menu");  // Button to return to menu
            endScoreLabel = new JLabel("Gratulacje, zdobywasz " + score + " punktów!");  // Display end score
            setLayout(new GridLayout(12, 1));  // Change layout to have more rows
            add(endScoreLabel, 0);
            add(goToMenuButton, 1);

            // Hide elements relating to questions
            questionLabel.setVisible(false);
            scoreLabel.setVisible(false);
            for (JButton button : answerButtons) {
                button.setVisible(false);
            }

            // Gets user's nick
            String username = CurrentUser.getInstance().getUser().getUsername();

            // Write quiz's end score to the corresponding .txt file in the ./stats folder
            try {
                // Define the file path
                java.nio.file.Path filePath = java.nio.file.Paths.get(String.format("./stats/%s.txt", currentCategory));

                // Create the contents to write
                String content = username + "|" + score + "\n";  // Format: <gracz>|<wynik>
                logger.info("Writing score for user: " + username + " to file: " + filePath);

                // Append the contents, if the file exists, or create a new one
                java.nio.file.Files.writeString(filePath, content, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
            } catch (java.io.IOException e) {
                logger.severe("Error writing score to file: " + e.getMessage());
            }

            // Handle going back to menu after quiz ends
            goToMenuButton.addActionListener(e -> {
                remove(questionLabel);
                remove(scoreLabel);
                for (JButton button : answerButtons) {
                    remove(button);
                }
                remove(goToMenuButton);
                remove(endScoreLabel);
                MenuView.goToMenu();  // Call method to go to menu
                logger.info("Returning to the main menu after quiz end.");
            });
        } catch (Exception e) {
            logger.severe("Error ending quiz: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method for loading new question
    private void loadNewQuestion(Category questions) {
        logger.info("Loaded a new question");
        try {
            if (shuffledQuestionsIndices.isEmpty() || currentQuestionIndex >= shuffledQuestionsIndices.size()) {
                questionLabel.setText("Brak pytań do wyświetlenia.");
                logger.warning("No more questions to display.");
                return;
            }
            
            // Get answer and shuffle their order
            List<String> answers = new ArrayList<>();
            answers = questions.getAnswers(shuffledQuestionsIndices.get(currentQuestionIndex));
            Collections.shuffle(answers);  // Przetasowanie odpowiedzi
            
            logger.info("Loading question " + (currentQuestionIndex + 1) + ": " + questions.getQuestion(shuffledQuestionsIndices.get(currentQuestionIndex)));
            // Set new question and answers on the buttons
            questionLabel.setText("" + (currentQuestionIndex + 1) + ". " + questions.getQuestion(shuffledQuestionsIndices.get(currentQuestionIndex)));
    
            // Initialize answer buttons
            Integer answerAmount = questions.getAnswers(shuffledQuestionsIndices.get(currentQuestionIndex)).size();
            for (int i = 0; i < defaultAnswerAmount; i++) {
                JButton newButton = answerButtons.get(i);
                // When there are less answers than implicitly
                if (i > answerAmount-1) {
                    newButton.setVisible(false);
                } else {
                    if (!newButton.isVisible()) { // If has gotten hidden previously
                        newButton.setVisible(true);
                    }
                    newButton.setText(answers.get(i));
                    
                    // Get rid of previous ActionListeners
                    for (ActionListener al : newButton.getActionListeners()) {
                        newButton.removeActionListener(al);
                    }
                    // Add ActionListeners to answer buttons
                    final Integer buttonID = i;
                    newButton.addActionListener(e -> {
                        handleClick(currentCategoryQuestions, newButton.getText(), buttonID);
                    });
                }
            }
    
            currentQuestionIndex++;  // Increment question counter
        } catch (Exception e) {
            logger.severe("Error loading new question: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method for checking the given answer
    private void checkAnswer(Category questions, String answer, Integer buttonId) {
        try {
            // Disable buttons once an answer has been chosen
            for (JButton button : answerButtons) {
                button.setEnabled(false);
            }

            // If it isn't the first question
            if (currentQuestionIndex != 0) {
                final int index = shuffledQuestionsIndices.get(currentQuestionIndex - 1);

                // Check if the answer is correct
                if (answer.equals(questions.getCorrectAnswers().get(index))) {
                    score++;  // Increment score
                    scoreLabel.setText("Wynik: " + score);  // Update current score

                    // Change the button's colour to green if the answer is correct
                    answerButtons.get(buttonId).setBackground(new Color(0, 255, 0));
                    logger.info("Correct answer selected. Current score: " + score);
                } else {
                    // If the answer is incorrect, change the correct button's color to green
                    for (int i = 0; i < questions.getAnswers(index).size(); i++) {
                        JButton button = answerButtons.get(i);
                        if (button.getText().equals(questions.getCorrectAnswers().get(index))) {
                            button.setBackground(new Color(0, 255, 0));
                        }
                    }

                    // Change the button's colour to red if the answer is incorrect
                    answerButtons.get(buttonId).setBackground(new Color(255, 0, 0));
                    logger.info("Incorrect answer selected. Current score: " + score);
                }
            }
        } catch (Exception e) {
            logger.severe("Error checking answer: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "An error occurred during logout. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}