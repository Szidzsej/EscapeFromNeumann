import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

  public static MySqlConnection connection;
  public static Player player;
  public static Scanner scanner;

  public static void main(String[] args) throws SQLException {

    connection = new MySqlConnection();

    if (!connection.makeConnection()){
      System.out.println("A MySQL kapcsolat meghiúsult!");
      return;
    }

    String rowname="classroom";
    int countanswerRows=connection.countOfRows(rowname);

    ArrayList<Classroom> classroomArrayList=new ArrayList<Classroom>();

    for(int i=1;i<countanswerRows+1;i++){
      classroomArrayList.add(connection.getClassRoomById(i));
    }
    scanner = new Scanner(System.in);
    System.out.println("Üdvözöljük az Escape from Neumann játékban!\nKérem adja meg a nevét:");

    String name = scanner.nextLine();
    player = new Player(1,name,100,0);
    System.out.println("Kedves "+player.getName()+", egy lenyűgöző kalandban fogsz részt venni, amiben megtapasztalhatod, milyen is a kemény Neumann-os élet.\nEbben a játékban az a célod, hogy kijuss az iskolából, méghozzá úgy, hogy megszerzed az iskola elhagyásához szükséges tudást, melyet úgy szerezhetsz, hogy kincseket gyűjtessz a tantermekben és/vagy lefelelsz a tanároknál.\nDe jól vigyázz! Ha valamelyik tanárnál nem sikerül a feleleted, önbizalmat vesztessz, mely ha elfogy véget ér a játék.\nDe ne csüggedj, az önbizalmadat növelheted potionokkal, és a kérdések megválaszolásában segítségedre lehet pár tárgy is, melyekkel ha nem rendelkezel vehetsz a büfében.\nÉs ha van benned vállalkozó szellem kipróbálhatod a varázs kávéautómatát is, mely egy csoda kávét készít neked, amit ha használsz,és a szerencse a te oldaladon van a felelet során helyesen válaszolsz majd,\nviszont ha a szerencse elpártolt tőled úgy helytelen választ fogsz megadni.");
    System.out.println("Kezdhetjük a játékot? igen  nem");
    String yesOrNo = scanner.nextLine();
    if(yesOrNo.equals("nem"))
    {
      System.exit(0);
    }
    else if(yesOrNo.equals("igen")){
      System.out.println("Sok szerencsét!");
    }
    NextRooms(classroomArrayList.get(0));
  }
  public static int Dice()
  {
    int temp = (int)(Math.random()*6+1);
    return temp;
  }

 public static Classroom NextRooms(Classroom ActualRoom) {
   System.out.println("Jelenleg " + ActualRoom.getName() + " helyiségben vagy!");
   HashMap<Integer, Classroom> temps = new HashMap<Integer, Classroom>();
   if (!ActualRoom.getName().equals("Udvar")) {
     InTheRoom(ActualRoom);
   }
   else if (player.getKnowledge() > 100)
   {
     FinalBoss(ActualRoom);
   }
    int count =1;
    for (Integer i:ActualRoom.getNextrooms().values())
    {
      try {
        temps.put(count, connection.getClassRoomById(i));
        System.out.println(count+ ". opció: "+connection.getClassroomNameById(i));
      } catch (SQLException e) {
        e.printStackTrace();
      }
      count++;
    }
    scanner = new Scanner(System.in);
    System.out.println("Add meg a sorszámát a helyiségnek, ahová átszeretnél menni: ");

    Integer name = Integer.parseInt(scanner.nextLine());

    return NextRooms(temps.get(name));
  }
  public static void InTheRoom(Classroom ActualRoom)
  {
    if (ActualRoom.hasTeacher() && !ActualRoom.getTeacher().getDefeated())
    {
      System.out.println("Vigyázat! A teremben tanár tartozkodik!\nDobj dobókockával és ha nem 6-ost dobsz, le kell felelned!");
      System.out.println("Dobok a kockával! [NYOMJ ENTERT]");
      scanner = new Scanner(System.in);
      scanner.nextLine();
      int result = Dice();
      System.out.println(result+" lett a dobás értéke!");
      if (result != 6)
      {
        Battle(ActualRoom.getTeacher());
        lootItem(ActualRoom);
      }
      else
      {
        System.out.println("A tanár rád néz gúnyos szemmel, majd elhagyja a termet!");
        System.out.println("Néz körbe mi van a teremben!");
        lootItem(ActualRoom);
      }
    }
  }
  public static void Battle(Teacher teacher)
  {
    Subject s = teacher.getSubject();
    System.out.println("Tanár neve: "+teacher.getName() + " Tantárgy: "+s.getName());

    int qCount = 1;
    int aCount = 1;
    for (Question q: s.getQuestions()) {
      if (player.getHP() > 0) {
        System.out.println(qCount + ". kérdés : " + q.getQuestion());
        for (String answer : q.getAnswers()) {
          System.out.println(aCount + ". válasz : " + answer);
          aCount++;
        }
         scanner = new Scanner(System.in);
        System.out.println("Add meg a sorszámát a helyes válasznak: ");
        Integer good = Integer.parseInt(scanner.nextLine());
        if (q.getCorrectAnswerID() == good - 1) {
          System.out.println("Helyes válasz! +1 tudás pont!");
          player.goodAnswer();
        } else {
          System.out.println("Rossz válasz! -10 önbizalom pont!");
          player.wrongAnswer();
        }
        aCount = 1;
        qCount++;
      }else
      {
        System.out.println("Köszönjük a játékot! Elfogyott az önbizalmad, a játéknak vége!");
        System.exit(0);
      }
    }
  }
  public static void FinalBoss(Classroom ActualRoom)
  {
    System.out.println("Itt a lehetőség, hogy elhagyd az iskolát!\n De ez előtt még egy utolsó felelet vár rád!");
    System.out.println("Készülj a végső összecsapásra!");
    Battle(ActualRoom.getTeacher());
    System.out.println("Gratulálunk a sikerhez! Végig vitted a játékot!");
  }
  public static void lootItem(Classroom classroom){
    ArrayList<Treasure> treasures=classroom.getTreasures();
    ArrayList<Item> potions=classroom.getPotions();
    int count=1;
    HashMap<Integer,String> itemInfos= new HashMap<Integer, String>();
    String nameOfItem="";

    System.out.println("Kérem válasszon az alábbi jutalmak közül egyet a mellette álló szám beírásával: ");

    for (Treasure tr:treasures) {
      nameOfItem=tr.getName();
      itemInfos.put(count,nameOfItem);
      System.out.println(count+".: "+nameOfItem);
      count++;
    }
    for (Item it:potions) {
      nameOfItem=it.getName();
      itemInfos.put(count,nameOfItem);
      System.out.println(count+".: "+nameOfItem);
      count++;
    }
    scanner = new Scanner(System.in);
    int chosenItemNumber = Integer.parseInt(scanner.nextLine());
    nameOfItem=itemInfos.get(chosenItemNumber);
    String finalNameOfItem = nameOfItem;
    Treasure treasure = treasures.stream()
            .filter(treasure1 -> finalNameOfItem.equals(treasure1.getName()))
            .findAny()
            .orElse(null);
    Item item = potions.stream()
            .filter(potion -> finalNameOfItem.equals(potion.getName()))
            .findAny()
            .orElse(null);
    if (item != null)
      player.setOneItem(item);
    if(treasure != null) {
      player.setOneTresureItem(treasure);
    }


    treasures.remove(treasure);
    potions.remove(item);
  }
}
