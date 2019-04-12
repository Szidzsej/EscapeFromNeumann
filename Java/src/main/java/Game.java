import java.sql.SQLException;
import java.util.ArrayList;

public class Game {

  public static MySqlConnection connection;

  public static void main(String[] args) throws SQLException {

    connection = new MySqlConnection();

    if (!connection.makeConnection()){
      System.out.println("A MySQL kapcsolat meghiúsult!");
      return;
    }


//Lekérdezések tesztelése - törölhető

    Teacher teacher = connection.getTeacherByClassroomId(6);
    System.out.println(teacher.getName());

    Subject subject = teacher.getSubject();

    System.out.println(subject.getName());
    for (Question question: subject.getQuestions()) {
      System.out.println(question.getQuestion());
      for (String answer: question.getAnswers()) {
        System.out.println("\t"+answer);
      }
      System.out.println("Helyes válasz:"+(question.getCorrectAnswerID()+1)+"\n");
    }

    ArrayList<Treasure> treasures = connection.getTreasuresByClassroomId(6);
    for (Treasure treasure:treasures) {
      System.out.println(treasure.getName()+" "+treasure.getValue());
    }
    System.out.println();

    ArrayList<Item> items = connection.getItemsByClassroomId(6);

    for (Item item: items) {
      System.out.println(item.getName()+" "+item.getValue()+" "+item.getType().getType());
    }

    Classroom classroom = connection.getClassRoomById(6);
    System.out.println(classroom.getName());

//lekérdezés tesztelés vége.








  }
}
