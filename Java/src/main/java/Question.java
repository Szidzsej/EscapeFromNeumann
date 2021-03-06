import java.util.ArrayList;
import java.util.HashMap;

/**
 * a kerdeseket definialjuk
 */
public class Question {
  private int id;
  private String question;
  private ArrayList<String> answers;
  private int correctAnswerID; // a helyes valasz indexe, azaz 0,1,2

  public Question(int id, String question, HashMap<String, Boolean> answersHashMap)
  {
    this.id = id;
    this.question = question;
    this.answers = new ArrayList<String>();
    this.FillAnswers(answersHashMap);
  }

    public String getQuestion() {
        return question;
    }

    /**
     * feltolti a valaszokat
     * @param answersHashMap valaszt tartalmazza, es azt, hogy helyes.e
     */
    private void FillAnswers(HashMap<String, Boolean> answersHashMap){
      int i = 0;
        for (String key: answersHashMap.keySet()) {
            this.answers.add(key);
            if (answersHashMap.get(key)){
                this.correctAnswerID=i;
            }
            i++;
        }
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswerID() {
        return correctAnswerID;
    }
}
