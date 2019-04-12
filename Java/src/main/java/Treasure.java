public class Treasure {
  private int id;
  private String name;
  private int value;

  public Treasure(int id, String name, int value) {
    this.id = id;
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public int getValue() {
    return value;
  }
}
