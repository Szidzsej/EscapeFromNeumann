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
  public void veryWrongAnswer()
  {
    this.hp -=20;
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
  public ArrayList<Item> getItems() { return  this.items;}
  public int useItem(Item item, Question q)
  {
    switch (item.getType().getType())
    {
      case GOODANSWER: item.setUsed(); return q.getCorrectAnswerID();
      case WRONGANSWER: item.setUsed(); if (q.getCorrectAnswerID() == 0) return 1; else return 0;
      case QUESTIONSKIPPER: item.setUsed(); this.knowledge--; return q.getCorrectAnswerID();
    }
    return 0;
  }
  public void usePotion(Item item){
    this.hp+=20;
    item.setUsed();
  }
}
