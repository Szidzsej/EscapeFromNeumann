public class Teacher {
  private int id;
  private String name;
  private Subject subject;
  private boolean isItDefeated;

  public Teacher(int id, String name, Subject subject)
  {
    this.id = id;
    this.name = name;
    this.subject = subject;
    this.isItDefeated = false;
  }
}
