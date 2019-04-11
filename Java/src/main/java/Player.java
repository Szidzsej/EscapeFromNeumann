import java.util.ArrayList;

public class Player {
  private int id;
  private String name;
  private int hp;
  private int knowledge;
  private ArrayList<Treasure> treasures;
  private ArrayList<Item> items;

  public Player(int id, String name, int hp, int knowledge)
  {
    this.id = id;
    this.name = name;
    this.hp = hp;
    this.knowledge = knowledge;
    treasures = new ArrayList<Treasure>();
    items = new ArrayList<Item>();
  }
}
