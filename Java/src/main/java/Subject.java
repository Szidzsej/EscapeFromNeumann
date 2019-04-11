import java.util.ArrayList;

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
}
