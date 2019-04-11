import java.util.ArrayList;

public class Classroom {
  private int id;
  private String name;
  private Teacher teacher;
  private ArrayList<Treasure> treasures;
  private ArrayList<Item> potions;

  public Classroom(int id,String name, Teacher teacher)
  {
    this.id = id;
    this.name = name;
    this.teacher = teacher;
    this.treasures = new ArrayList<Treasure>();
    this.potions = new ArrayList<Item>();
  }
}
