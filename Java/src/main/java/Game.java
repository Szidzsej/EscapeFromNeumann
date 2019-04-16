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
    System.out.println("udvozoljuk az Escape from Neumann jatekban!\nKerem adja meg a nevet:");

    String name = scanner.nextLine();
    player = new Player(1,name,100,0);
    System.out.println("Kedves "+player.getName()+", egy lenyugozo kalandban fogsz reszt venni, amiben megtapasztalhatod, milyen is a kemeny Neumann-os elet.\nEbben a jatekban az a celod, hogy kijuss az iskolabol, meghozza úgy, hogy megszerzed az iskola elhagyasahoz szukseges tudast, melyet úgy szerezhetsz, hogy kincseket gyujtessz a tantermekben es/vagy lefelelsz a tanaroknal.\nDe jol vigyazz! Ha valamelyik tanarnal nem sikerul a feleleted, onbizalmat vesztessz, mely ha elfogy veget er a jatek.\nDe ne csuggedj, az onbizalmadat novelheted potionokkal, es a kerdesek megvalaszolasaban segitsegedre lehet par targy is, melyekkel ha nem rendelkezel vehetsz a bufeben.\nes ha van benned vallalkozo szellem kiprobalhatod a varazs kaveautomatat is, mely egy csoda kavet keszit neked, amit ha hasznalsz,es a szerencse a te oldaladon van a felelet soran helyesen valaszolsz majd,\nviszont ha a szerencse elpartolt toled úgy helytelen valaszt fogsz megadni.");
    System.out.println("Kezdhetjuk a jatekot? Igen  Nem");
    String yesOrNo = scanner.nextLine();
    if(yesOrNo.equals("igen")|| yesOrNo.equals("Igen")){
      System.out.println("Sok szerencset!");
    }
    else{
      System.exit(0);
    }
    NextRooms(classroomArrayList.get(0));
  }

  /**
   * kockadobas
   * @return a szam, amit dobott
   */
  public static int Dice()
  {
    int temp = (int)(Math.random()*6+1);
    return temp;
  }

  /**
   * kiirja a jatekos jelenleg melyik szobaban talalhato es a kapcsolodo szobak neveit  es sorszamait irja ki
   * a kivalasztott szoba sorszamanak beirasaval be is lep a jatekos a szobaba, mely utan ismet kiirja a kapcsolodo szobakat
   * @param ActualRoom jelenlegi szoba
   * @return sajat magat hivja meg a kovetkezo szobaval
   */
 public static Classroom NextRooms(Classroom ActualRoom) {
   System.out.println("Jelenleg " + ActualRoom.getName() + " helyisegben vagy!");
   HashMap<Integer, Classroom> temps = new HashMap<Integer, Classroom>();
   if (!ActualRoom.getName().equals("Udvar") && !ActualRoom.getName().equals("Bufe")) {
     InTheRoom(ActualRoom);
   }
   else if (player.getKnowledge() > 100 && !ActualRoom.getName().equals("Bufe"))
   {
     FinalBoss(ActualRoom);
   }
   else if(ActualRoom.getName().equals("Bufe")) {
     Buffet();
   }
    int count =1;
    for (Integer i:ActualRoom.getNextrooms().values())
    {
      try {
        temps.put(count, connection.getClassRoomById(i));
        System.out.println(count+ ". opcio: "+connection.getClassroomNameById(i));
      } catch (SQLException e) {
        e.printStackTrace();
      }
      count++;
    }
    scanner = new Scanner(System.in);
    System.out.println("Add meg a sorszamat a helyisegnek, ahova atszeretnel menni: ");

    Integer name = Input(scanner.nextLine(),ActualRoom.getNextrooms().size());
    if (name == 0)
    {
      cls();
      return NextRooms(ActualRoom);
    }

    return NextRooms(temps.get(name));
  }

  /**
   * ha van tanar a teremben dob a jatekos, ha 6-ost dob, nem kell felelnie, es lootolhat a teremben levo itemek kozul egyet, ha nem 6-os elindul a felelet   *
   * @param ActualRoom jelenlegi szoba
   */
  public static void InTheRoom(Classroom ActualRoom)
  {
    if (ActualRoom.hasTeacher() && !ActualRoom.getTeacher().getDefeated())
    {
      System.out.println("Vigyazat! A teremben tanar tartozkodik!\nDobj dobokockaval es ha nem 6-ost dobsz, le kell felelned!");
      System.out.println("Dobok a kockaval! [NYOMJ ENTERT]");
      scanner = new Scanner(System.in);
      scanner.nextLine();
      int result = Dice();
      System.out.println(result+" lett a dobas erteke!");
      if (result != 6)
      {
        Battle(ActualRoom.getTeacher());
        lootItem(ActualRoom);
      }
      else
      {
        System.out.println("A tanar rad nez gúnyos szemmel, majd elhagyja a termet!");
        System.out.println("Nez korbe mi van a teremben!");
        lootItem(ActualRoom);
      }
    }
  }

  /**
   * itt felel le a jatekos:a kiirt kerdesre a valaszok kozul valaszt egyet a szama alapjan, ha helyes a valasz, kap tudast, ha helytelen onbizalmat veszit
   * 5 koros egy felelet
   * ha elfogy a jatekos onbizalma, veget er a jatek
   * helyes felelet utan a tanar "legyozettetett", tobbet nem lehet nala felelni
   * @param teacher a tanar, aki felelteti a jatekost
   */
  public static void Battle(Teacher teacher)
  {
    Subject s = teacher.getSubject();
    System.out.println("Tanar neve: "+teacher.getName() + " Tantargy: "+s.getName());

    int qCount = 1;
    int aCount = 1;
    for (Question q: s.getQuestions()) {
      if (player.getHP() > 0) {
        System.out.println(qCount + ". kerdes : " + q.getQuestion());
        for (String answer : q.getAnswers()) {
          System.out.println(aCount + ". valasz : " + answer);
          aCount++;
        }
        System.out.println(aCount+ ". Segitseg/Potion hasznalata!");
         scanner = new Scanner(System.in);
        System.out.println("Add meg a sorszamat a helyes valasznak: ");
        Integer good = Input(scanner.nextLine(), 4);
        if  (good == 4) {
          good = getPlayersItems(q);
        }
        if (good == 10 || good == 404)
        {
          System.out.println("Add meg a sorszamat a helyes valasznak: ");
          good = Integer.parseInt(scanner.nextLine());
        }
        if (q.getCorrectAnswerID() == good - 1) {
          System.out.println("Helyes valasz! +1 tudas pont!");
          player.goodAnswer();
        } else if(good == 0){
          System.out.println("Nagyon rossz valasz! Teljesen rossz, meg a sorszamot se talaltad el! -20 onbizalom pont!");
          player.veryWrongAnswer();
        }else
        {
          System.out.println("Rossz valasz! -10 onbizalom pont!");
          player.wrongAnswer();
        }
        aCount = 1;
        qCount++;
      }else
      {
        System.out.println("Koszonjuk a jatekot! Elfogyott az onbizalmad, a jateknak vege!");
        System.exit(0);
      }
    }
    cls();
    teacher.setDefeated();
  }

  /**
   * a vegso harc
   * @param ActualRoom jelenlegi szoba, mely ez esetben az udvar
   */
  public static void FinalBoss(Classroom ActualRoom)
  {
    System.out.println("Itt a lehetoseg, hogy elhagyd az iskolat!\n De ez elott meg egy utolso felelet var rad!");
    System.out.println("Keszulj a vegso osszecsapasra!");
    Battle(ActualRoom.getTeacher());
    System.out.println("Gratulalunk a sikerhez! Vegig vitted a jatekot!");
  }

  /**
   * a tanteremben talalhato jutalmak kozul itt valaszt a jatekos a megadott sorszamok alapjan
   * amit valaszt, az a jatekoshoz kerul, kiveve, ha nem megfelelo szamot ir be
   * ha a jatekos megszerzett egy targyat, az eltunik a szobabol
   * @param classroom aktualis tanterem ahol lootolsz
   */
  public static void lootItem(Classroom classroom){
    ArrayList<Treasure> treasures=classroom.getTreasures();
    ArrayList<Item> potions=classroom.getPotions();
    int count=1;
    HashMap<Integer,String> itemInfos= new HashMap<Integer, String>();
    String nameOfItem="";

    System.out.println("Kerem valasszon az alabbi jutalmak kozul egyet a mellette allo szam beirasaval: ");

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
    int chosenItemNumber = Input(scanner.nextLine(),(treasures.size()+potions.size()));
    if (chosenItemNumber == 0)
    {
      System.out.println("Mi kertuk, hogy a jutalmak kozul valasszon! Rosszul dontottel, elvesztetted a jutalmakat!");
      return;
    }
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

  /**
   * itt adja ki a jatekosnal levo targyakat, itemeket
   * @param question kerdes, amire hasznalja el a jatekos a kivalasztott itemet
   * @return tipusonkent eltero a visszateres
   */
  public static int getPlayersItems(Question question){
    int count=1;
    int temp;
    HashMap<Integer,String> itemInfos= new HashMap<Integer, String>();
    if(player.getItems().size() >0) {
      System.out.println("Kerem valasszon az alabbi segitsegek kozul egyet a mellette allo szam beirasaval: ");
      for (Item it : player.getItems()) {
        if (!it.getUsed()) {
          System.out.println(count + ".: " + it.getName());
          itemInfos.put(count, it.getName());
          count++;
        }
      }
      scanner = new Scanner(System.in);
      int chosenItemNumber = Input(scanner.nextLine(), count - 1);
      String finalNameOfItem = itemInfos.get(chosenItemNumber);
      Item item = player.getItems().stream()
              .filter(potion -> finalNameOfItem.equals(potion.getName()))
              .findAny()
              .orElse(null);
      if (item.getType().getType() == Enums.ItemKind.SELFCONFIDENCE) {
        player.usePotion(item);
        temp = 10;
        return temp;
      } else {
        temp = player.useItem(item, question);
        return temp;
      }
    }
    else
    {
      System.out.println("Nincs segitseged, vagy potionod jelenleg!");
      return 404;
    }
  }

  /**
   * itt van a bufe, ahol a jatekos "vasarolhat" itemet
   * el kell mondania egy tortennetet, ha a bufes neninek tetszett kap egy itemet
   */
  public static void Buffet()
  {
    System.out.println("Jo napot! udv a bufemben! Mit szeretnel venni?");
    System.out.println("Micsoda? Nincs semmi penzed? Nincs zsebpenzed, mert megbuktal felevkor?");
    System.out.println("Hat akkor most mi lesz?");
    System.out.println("Sajnos nem nincs penzed, ezert úgy dontesz, hogy leallsz pletykalni a bufes nenivel! Hatha add valami jot!");

    scanner = new Scanner(System.in);
    System.out.println("A sztorid: ");
    String story = scanner.nextLine();
    boolean accepted = false;
    ItemType item = null;
    int length = story.length();
    if (length%3==0)
    {
      accepted = true;
    }
    if (accepted) {
      int dice = Dice();
      try {
        item = connection.getOneItemType(dice);
        player.setOneItem(new Item(player.getItems().size(),item.getName(),item,0));
      } catch (SQLException e) {
        e.printStackTrace();
      }
      System.out.println("Bufes nenike nagyon tetszett, a mondanivalod! Gratulalok!");
      System.out.println("Ajandekod egy "+item.getName()+"!");
    }else
    {
      System.out.println("Nem sikerult meggyoznod a bufes nenit! :/");
    }
  }

  /**
   * ellenorzi a bemenetet
   * @param inputLine jatekos alltal bevitt adat
   * @param count lehetosegek szama
   * @return 0,ha nem megfelelot adott meg, egyebkent a bevitt adat
   */
  public static int Input(String inputLine, int count)
  {
    int temp = 0;
    if (tryParseInt(inputLine)) {
      if (count >= Integer.parseInt(inputLine) && Integer.parseInt(inputLine) >= 1)
        temp = Integer.parseInt(inputLine);
    }else {
      temp = 0;
    }
    return temp;
  }

  /**
   * szamma alakitas
   * @param value amit atalakit
   * @return sikerul-e szamma alakitani
   */
  public static boolean tryParseInt(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static void cls()
  {
    try
    {
      Runtime.getRuntime().exec("cmd /c cls");
    }
    catch(final Exception e)
    {
      System.out.print(e);
    }

  }
}
