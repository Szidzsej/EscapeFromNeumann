public class Item {
  private int id;
  private String name;
  private ItemType type;
  private int value;
  private boolean isItUsed;

  public Item(int id, String name, ItemType type, int value)
  {
    this.id = id;
    this.name = name;
    this.type = type;
    this.value = value;
    this.isItUsed = false;
  }

  public int getValue() {
    return value;
  }

  public String getName() {
    return name;
  }

  public ItemType getType() {
    return type;
  }
  public void setUsed() { this.isItUsed = true;}
  public boolean getUsed() { return this.isItUsed;}
}
