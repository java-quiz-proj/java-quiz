package questions;

import java.util.ArrayList;
import java.util.List;

public class Maths implements Category {

        private List<String> questions = new ArrayList<>();
        private List<String> correctAnswers = new ArrayList<>();
        private List<String> answers1 = new ArrayList<>();
        private List<String> answers2 = new ArrayList<>();
        private List<String> answers3 = new ArrayList<>();
        private List<String> answers4 = new ArrayList<>();

        public Maths(){

            questions.add("sin(90 deg) = ?");
            correctAnswers.add("1");
            answers1.add("1");
            answers2.add("~-1,7");
            answers3.add("0");
            answers4.add("~1,5");

            questions.add("Jaka jest najmniejsza liczba pierwsza?");
            correctAnswers.add("2");
            answers1.add("2");
            answers2.add("3");
            answers3.add("5");
            answers4.add("1");

            questions.add("Która z tych liczb ma najwięcej dzielników?");
            correctAnswers.add("12");
            answers1.add("12");
            answers2.add("20");
            answers3.add("10");
            answers4.add("15");

            questions.add("Które z tych przybliżeń pi jest najdokładniejsze");
            correctAnswers.add("3,14159");
            answers1.add("3,14159");
            answers2.add("3.14253");
            answers3.add("3,14227");
            answers4.add("3,14149");

            questions.add("Jak wygląda wzór na pole koła?");
            correctAnswers.add("pi*r*r");
            answers1.add("2*pi*r");
            answers2.add("r*r");
            answers3.add("pi*r*r");
            answers4.add("a*h/2");
        }

        @Override
        public List<String> getCorrectAnswers() {
            return correctAnswers;
        }
        @Override
        public List<String> getQuestions() {
            return questions;
        }
        @Override
        public String getQuestion(int n) {
            return questions.get(n);
        }
        @Override
        public List<String> getAnswers1() {
            return answers1;
        }
        @Override
        public List<String> getAnswers2() {
            return answers2;
        }
        @Override
        public List<String> getAnswers3() {
            return answers3;
        }
        @Override
        public List<String> getAnswers4() {
            return answers4;
        }


}


