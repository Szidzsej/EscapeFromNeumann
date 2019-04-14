import java.util.ArrayList;
import java.util.HashMap;

public class Classroom {
  private int id;
  private String name;
  private Teacher teacher;
  private ArrayList<Treasure> treasures;
  private ArrayList<Item> potions;
  private HashMap<String, Integer> nextrooms = new HashMap<String, Integer>();

  public Classroom(int id, String name, Teacher teacher, ArrayList<Treasure> treasures, ArrayList<Item> potions, HashMap<String, Integer> nexts) {
    this.id = id;
    this.name = name;
    this.teacher = teacher;
    this.treasures = treasures;
    this.potions = potions;
    this.nextrooms = nexts;
  }

  public String getName() {
    return name;
  }

  public int getID() {
    return this.id;
  }

  public HashMap<String, Integer> getNextrooms() {
    return this.nextrooms;
  }

  public boolean hasTeacher() {
    if (this.teacher != null)
      return true;
    else
      return false;
  }

  public Teacher getTeacher()
  {
    return this.teacher;
  }

    public ArrayList<Item> getPotions() {
      return this.potions;
    }

    public ArrayList<Treasure> getTreasures() {
      return this.treasures;
    }
}
