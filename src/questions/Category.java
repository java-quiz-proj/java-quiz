package questions;

import java.util.List;

public interface Category {


         List<String> getCorrectAnswers();

         List<String> getQuestions();

         String getQuestion(int n);

         List<String> getAnswers(int n);

}
