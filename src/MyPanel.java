import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class MyPanel extends JPanel {
    private int points;
    private JLabel questionLabel, pointsLabel;
    private JButton buttonA, buttonB, buttonC, buttonD;
    //poniższa lista zawierać będzie liczby odpowiadające numorowi pytań
    private List<Integer> qnr = new ArrayList<>();
    private int questionNr;
    public MyPanel() {
        points = 0;
        questionLabel = new JLabel("" + questionNr);
        pointsLabel = new JLabel("score: " + points);
        buttonA = new JButton("");
        buttonB = new JButton("");
        buttonC = new JButton("");
        buttonD = new JButton("");

        questionNr = 0;
        Questions questions = new Questions();

        for (int i = 0; i <= questions.getQuestions().size(); i++) {
            qnr.add(i);
        }
        Collections.shuffle(qnr);

        buttonA.addActionListener(e -> {
            click(questions, buttonA.getText(), "A");
        });

        buttonB.addActionListener(e->{
            click(questions, buttonB.getText(), "B");
        });

        buttonC.addActionListener(e->{
            click(questions, buttonC.getText(), "C");
        });

        buttonD.addActionListener(e->{
            click(questions, buttonD.getText(), "D");
        });

        add(questionLabel);
        add(pointsLabel);
        setLayout(new GridLayout(3, 2));
        add(buttonA);
        add(buttonB);
        add(buttonC);
        add(buttonD);
        newQuestion(questions);

    }

    private void click(Questions questions, String answer, String btn){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            if(questionNr<questions.getQuestions().size()) {

                newQuestion(questions);

            }
            else questionLabel.setText("koniec pytań");

                buttonA.setBackground(null);
                buttonB.setBackground(null);
                buttonC.setBackground(null);
                buttonD.setBackground(null);

                }
            }, 2000); // wywołanie po 2 sekundach

        //checking if the answer is correct or not
        answerCheck(questions, answer, btn);
    }

    private void newQuestion(Questions questions){
        questionLabel.setText(""+(questionNr+1)+". "+questions.getQuestion(questionNr));
        buttonA.setText(questions.getAnswers1().get(questionNr));
        buttonB.setText(questions.getAnswers2().get(questionNr));
        buttonC.setText(questions.getAnswers3().get(questionNr));
        buttonD.setText(questions.getAnswers4().get(questionNr));
        questionNr ++;
    }

    private void answerCheck(Questions questions, String answer, String btn){
        if(questionNr!=0){
            if(answer.equals(questions.getCorrectAnswers().get(questionNr-1))) {
                points++;
                pointsLabel.setText("score: " + points);

                switch(btn){
                    case "A" -> buttonA.setBackground(new Color(0, 255, 0));
                    case "B" -> buttonB.setBackground(new Color(0, 255, 0));
                    case "C" -> buttonC.setBackground(new Color(0, 255, 0));
                    case "D" -> buttonD.setBackground(new Color(0, 255, 0));
                }
            }else {

                if(buttonA.getText().equals(questions.getCorrectAnswers().get(questionNr-1)))
                    buttonA.setBackground(new Color(0, 255, 0));
                if(buttonB.getText().equals(questions.getCorrectAnswers().get(questionNr-1)))
                    buttonB.setBackground(new Color(0, 255, 0));
                if(buttonC.getText().equals(questions.getCorrectAnswers().get(questionNr-1)))
                    buttonC.setBackground(new Color(0, 255, 0));
                if(buttonD.getText().equals(questions.getCorrectAnswers().get(questionNr-1)))
                    buttonD.setBackground(new Color(0, 255, 0));

                switch (btn) {
                    case "A" -> buttonA.setBackground(new Color(255, 0, 0));
                    case "B" -> buttonB.setBackground(new Color(255, 0, 0));
                    case "C" -> buttonC.setBackground(new Color(255, 0, 0));
                    case "D" -> buttonD.setBackground(new Color(255, 0, 0));
                }
            }
        }
    }
}
