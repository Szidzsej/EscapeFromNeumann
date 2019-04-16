import java.util.ArrayList;

/**
 * a tantargyak adatait itt definialjuk
 */
public class Subject {
  private int id;
  private String name;
  private ArrayList<Question> questions;

  public Subject(int id, String name, ArrayList<Question> questions)
  {
      this.id = id;
      this.name = name;
      this.questions = questions;
  }

    public String getName() {
        return name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
