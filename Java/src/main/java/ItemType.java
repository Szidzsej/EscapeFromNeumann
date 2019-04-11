public class ItemType extends Enums{
  private int id;
  private String name;
  private ItemKind type;

  public ItemType(int id, String name, ItemKind itemKind)
  {
    this.id = id;
    this.name = name;
    this.type = itemKind;
  }
}
