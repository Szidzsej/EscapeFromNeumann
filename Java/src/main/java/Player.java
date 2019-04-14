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
  public void goodAnswer()
  {
    this.knowledge++;
  }
  public void wrongAnswer()
  {
    this.hp -=10;
  }
  public int getHP()
  {
    return this.hp;
  }

  public int getKnowledge() {
    return knowledge;
  }
  public String getName()
  {
    return this.name;
  }
  public void setOneTresureItem(Treasure t)
  {
      this.treasures.add(t);
      this.knowledge += t.getValue();
  }
  public void setOneItem(Item i)
  {
      this.items.add(i);
  }
}
