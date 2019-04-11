public class Question {
  private int id;
  private String question;
  private String[] answers;
  private int correctAnswerID;

  public Question(int id, String question, String[] answers, int correct)
  {
    this.id = id;
    this.answers = answers;
    this.question = question;
    this.correctAnswerID = correct;
  }
}
