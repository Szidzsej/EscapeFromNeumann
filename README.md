# EscapeFromNeumann  
JAVA BEADANDO  
JAVA TEXT-BASED RPG  
„Escape from Neumann”  
  
Térkép: Neumann school   
Szoba:Tanteremek, Tanári, Öltöző  
-->  class Area (int id, Teacher teacher, List<Treasure> treasure, List<Potion> potion)  
--> egy terembe több loot is lehet  
Kincsek:  
-->  Floppy +5 ,  
--> CD+10,  
--> Pendrive+20,  
--> Winchester+50  
--> --> class Treasure (int id, String name, int value)   
Szörnyek: Tanárok, ha észre vesz mikor lopod a dogák megoldásait, lefeleltetnek  
--> class Teacher (int id, String name, Subject subject, bool isItDefeated)  
--> class Subject (int id, String name, List<Question> questions)  
--> class Question (int id, String question, String[] answers, int correctAnswerID)  
Életerő: Önbizalom  
--> class Player (String name, int hp, int knowledge, List<Treasure> treasures, List<Potion> potions)  
Harc menete:  
--> Mikor belépsz egy terembe és van bent tanár, dobnod kell kockával és ha nem 6-os dobsz, lekell felelned  
  --> public int Dice() return randomInt(1,6); (vagy valami hasonló)  
--> Tanár feltesz 3-5 kérdést, ez véletlenszerű, a kérdések is véletlenszerüek,   
  --> public void bool Questions(Player player)   
--> Dice segítségével generálunk egy számot, hány kérdést rakjon fel az adott tanár abban a teremben, a teremhez van kapcsolva a tanár, a tanárhoz a kérdések  
--> 4 válasz lehetőség van  
--> Kérdés osztályban levan írva mik azok  
--> Ha nem tudod a választ akkor vesztesz az önbizalmadból  
--> Ha elfogy az önbizalmad akkor a játéknak vége, megfogsz bukni a vizsgán  
  --> public bool AreYouDead(Player player) if(player.hp ==0) return true;  
--> Ha elfogytak a kérdések, akkor kitudod lootolni a termet  
  --> if(area.teacher.isItDefeated) -> ird ki mi van a szobában  
--> Harc közben használhatsz „Varázstárgyakat”   
--> Minden jól megválaszolt kérdés +1 tudás pont  
--> Termekben 0-2 megtalálható „kincs” van  
  --> Dice-cal ez megoldaható, amit vissza kapsz értéket 1 és 6 között osztod maradékosan 3-mal a maradék a loot száma  
--> Loot lehet ott hagyott „Varázstárgy”  vagy tudás tartalmazó eszköz  
--> Az egyik terem a „Bűfé” itt tudsz venni „Varázstárgyakat”,  ezt megteheted szerzett eszközök becserélésével vagy storyzgatással a bűfés nénivel  
Varázstárgyak  
--> Önbizalom növelők  
  --> Go Energia ital  
  --> Kobra Energia ital  
  --> Szikkadt rántott húsos zsemle  
  --> Hamburgeres popcorn  
    --> class Items ( int id, string name, ItemType type, int value)  
    --> class ItemType(int id, string name, string type, bool isItUsed)   
      --> Önbizalom növelő  
      --> Kérdést kihagyó tárgy  
      --> Kérdésre megadja a jó választ Kávé Buff  
      --> Kérdésre rossz választ add Kávé Debuff  
      --> 1-szer használható  
