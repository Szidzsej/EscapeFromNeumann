import java.util.ArrayList;

public class Classroom {
  private int id;
  private String name;
  private Teacher teacher;
  private ArrayList<Treasure> treasures;
  private ArrayList<Item> potions;

  public Classroom(int id,String name, Teacher teacher, ArrayList<Treasure> treasures, ArrayList<Item> potions)
  {
    this.id = id;
    this.name = name;
    this.teacher = teacher;
    this.treasures = treasures;
    this.potions = potions;
  }

  public String getName() {
    return name;
  }
}
