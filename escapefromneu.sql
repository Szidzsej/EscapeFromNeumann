CREATE TABLE Classroom(
id int PRIMARY KEY AUTO_INCREMENT,
roomname varchar(250) NOT NULL,
nextrooms varchar(100)
);
Insert into Classroom (roomname,nextrooms) VALUES
("Udvar","2;"),
("Aula","1;3;4"),
("Büfé","2;"),
("Földszinti lépcsö","2;5;"),
("Elsö emeleti folyosó","4;6;7;8;9;"),
("Elsö emeleti Tanári","5;"),
("10-es gépterem","5;"),
("119-es terem","5;"),
("Elsö emeleti lépcsö","5;10;"),
("Második emeleti folyosó","9;11;12;13;"),
("9-es gépterem","10;"),
("7-es gépterem","10;"),
("205-ös terem","10;");
Create table TreasureType(
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(250) NOT NULL,
value int NOT NULL
);
INSERT INTO TreasureType (name, value)
Values
("Floppy",5),
("CD",10),
("Pendrive",15),
("Winchester",25),
("Dolgozat megoldások",50);

Create table Treasure(
id int PRIMARY KEY AUTO_INCREMENT,
treasureTypeID int NOT NULL,
classroomID int NOT NULL,
FOREIGN KEY (classroomID) References Classroom(id),
FOREIGN KEY (treasureTypeID) REFERENCES TreasureType(id)
);

Insert into Treasure (classroomID,treasureTypeID) VALUES
(6,1),
(6,2),
(6,5),
(7,3),
(7,1),
(7,5),
(8,3),
(8,1),
(8,2),
(11,2),
(11,3),
(11,4),
(12,3),
(12,2),
(12,1),
(13,4),
(13,3),
(13,1);
Create table ItemKind (
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(250) NOT NULL
);
Insert into ItemKind (name) Values
("Önbizalom"),
    ("Kihagyo"),
    ("KaveBuff"),
    ("KaveDebuff");
Create table Player(
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(250) NOT NULL,
knowledge int NOT NULL,
hp int NOT NULL
);

Create table Teacher(
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(250) NOT NULL,
classroomID int NOT NULL,
FOREIGN KEY (classroomID) REFERENCES Classroom(id)
);
Insert into Teacher (name,classroomID) VALUES
("R. Csaba",6),
("O. Kata",7), 
("V. Zoltán",8),
("Z. Gabi",11), 
("K. Csilla",12), 
("R. Gusztáv",13),
("V. Tibor",1);
Create table TeacherSubject(
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(250) NOT NULL,
teacherID int NOT NULL,
FOREIGN KEY (teacherID) REFERENCES Teacher(id)
);
INSERT INTO TeacherSubject (name,teacherID) VALUES
("CSharp",1),
("Bootstrap/Jquery",2),
("Operációs rendszerek",3),
("Történelem",4),
("Matematika",5),
("PHP",6),
("JAVA",7);
Create table Question(
id int PRIMARY KEY AUTO_INCREMENT,
question varchar(250) NOT NULL,
subjectID int NOT NULL,
FOREIGN KEY (subjectID) REFERENCES TeacherSubject(id)
);

Insert into Question (id,question, subjectID) VALUES
(1,"Mije van a Dictionary-nek?",1),
(2,"Mi a mátrix?",1),
(3,"Mi a PPP?",1),
(4,"Mire jó a var kifejezés?",1),
(5,"Mit rövidít az OOP kifejezés?",1),
(6,"Mi a tag-je a navigációs fejlécnek?",2),
(7,"HTML dokumentumban, hol hívjuk meg a jquery-t?",2),
(8,"Mondj egy layout-ot, ami van Bootstrapben?",2),
(9,"Mire jó a container?",2),
(10,"Mire kell használni a display:block, kód sort?",2),
(11,"Mondj egy monolitkus kernelt használó OP. rendszert!",3),
(12,"Mire jó az echo parancs?",3),
(13,"Hogy kérjük le a saját IP-cimünket?",3),
(14,"Linuxon hogy lépszbe rendszergazdai módba?",3),
(15,"Hogy hívják azt a jelenséget, amikor a processek, nem futnak tovább, mert egymásra várnak.",3),
(16,"Mikor adták ki az Aranybullát?",4),
(17,"Mikor volt a Mohácsi vész?",4),
(18,"Ki volt az utolsó Anjou király?",4),
(19,"Ki volt a király a tatár vész alatt?",4),
(20,"Mettöl meddig tartott az I. világháború?",4),
(21,"Hány csúcsa van egy parallelepipedonnak?",5),
(22,"Hogyan számoljuk ki a kör területét?",5),
(23,"Mennyi négyzetgyök 81?",5),
(24,"Mennyi 6 osztva 0-val?",5),
(25,"Mennyi a háromszög belsö szögeinak összege?",5),
(26,"Mit jelent az MVC?",6),
(27,"Mi a kiterjesztése egy php fájlnak?",6),
(28,"Hogyan jelöljük az szöveg összefüzését?",6),
(29,"Igaz-e az állítás? PHP erösen típusos nyelv!",6),
(30,"Minek a rövidítése a PHP?",6),
(31,"Mi a különbség a HashMap és a HashTable között?",7),
(32,"Lehetséges break-ezni egy beágyazott ciklusból Javában?",7),
(33,"Melyiket érdemes használni a 2 közül? 'implements Runnable' vagy 'extends Thread'",7),
(34,"Mi a különbség a float és double között?",7),
(35,"Mi az a ös osztály, amiböl az összes többi Java osztály származik?",7);

Create table Answer(
id int PRIMARY KEY AUTO_INCREMENT,
answer varchar(250) NOT NULL,
questionID int NOT NULL,
isItCorrect tinyint NOT NULL,
Foreign KEY (questionID) REFERENCES Question(ID)
);
Insert into Answer (answer,questionID,isItCorrect) Values
("Kulcs-érték párja",1,1),
("Kulcs-zár párja",1,0),
("Kanala-érem párja",1,0),
("Egy jó film!",2,0),
("2 dimenziós tömb",2,1),
("2 elemü tömb",2,0),
("Private Proctect Public",3,0),
("Perui palota pincsi",3,0),
("Private Proctected Public",3,1),
("Felveszi az egyenlöség jobb oldalán található objektum/változó típusát!",4,1),
("Kertet ásni!",4,0),
("Metódusunkhoz több variációt készíteni!",4,0),
("Országos Orkán Projekt",5,0),
("Objektum Orientált Programozás",5,1),
("Orosházai Oboa Party",5,0),
("<menu>",6,0),
("<bar>",6,0),
("<nav>",6,1),
("Body aljára!",7,1),
("Head-ben!",7,0),
("Nem kell meghívni, már vagyunk elegen!",7,0),
("Focus",8,0),
("Flex",8,1),
("Gerenda",8,0),
("Abban tároljuk a tartalmat!",9,1),
("Abba rakjuk a sitet!",9,0),
("Megvizsgálni egy tömböt, hogy egy adott elem megtalálható-e benne.",9,0),
("Kikapcsolja a monitort!",10,0),
("Az elemünket kocka alakzatúvá alakítja!",10,0),
("Blokk szintü elemmé alakítás!",10,1),
("DDOS",11,0),
("Linux",11,1),
("Operation társas",11,0),
("Konzolra való kiírásra!",12,1),
("CS GO-ban echo körben nem veszünk semmit!",12,0),
("Viszhangot generál!",12,0),
("ip-download paranccsal!",13,0),
("Felhívjuk a szolgáltatót!",13,0),
("ip-config paranccsal!",13,1),
("sudo-val",14,1),
("Érzékkel!",14,0),
("Bal lábbal!",14,0),
("Padhelyzet",15,0),
("Deadlock, holtpont",15,1),
("Kékhalál",15,0),
("Ezüst bulla után!",16,0),
("1222-ben",16,1),
("1848-ban",16,0),
("1526-ban",17,1),
("Tavaly augusztusban!",17,0),
("Nem tudom, sok ittam akkoriban!",17,0),
("Habsburg Ferdinánd",18,0),
("IV. Béla",18,0),
("Anjou László",18,1),
("IV. Béla",19,1),
("Horthy Miklós",19,0),
("Nem volt király, mert császárság volt!",19,0),
("1914-1918",20,1),
("1914-1919",20,0),
("1939-1945",20,0),
("8",21,1),
("Az meg mi?",21,0),
("16",21,0),
("2*r*PI",22,0),
("r négyzet * PI",22,1),
("360 fok",22,0),
("6561",23,0),
("9",23,1),
("81",23,0),
("6",24,0),
("0",24,0),
("Nincs 0-val való osztás!",24,1),
("180 fok",25,1),
("360 fok",25,0),
("3",25,0),
("Magyar Vándor Cirkusz",26,0),
("Model View Connector",26,0),
("Model View Controller",26,1),
(".php",27,1),
(".html",27,0),
("Nagy",27,0),
("Szóközzel",28,0),
("+ jellel",28,0),
("Ponttal",28,1),
("Igaz",29,0),
("Hamis",29,1),
("Kérem a következöt!",29,0),
("Hypertext Preprocessor",30,1),
("Pre Hypertext Processor",30,0),
("Portugál hegyi pávián",30,0),
("Egyik kulcs érték párral dolgozik a másik nem!",31,0),
("Egyik egy pálya, a másik a pályán lévö asztal",31,0),
("Hashmap engedélyez null kulcsot, a hashtable nem!",31,1),
("Nem",32,0),
("Mi az a ciklus?",32,0),
("Igen",32,1),
("Runnable",33,1),
("Thread",33,0),
("Egyiket se, nekem van egy jobb megoldásom, de nem mondom meg!",33,0),
("Ugyanaz csak szinonimák",34,0),
("double 4 bites, a float 8 bites",34,1),
("double 8 bites, a float 4 bites",34,0),
("java.base",35,0),
("java.class",35,0),
("java.lang.object ",35,1);
Create table ItemType(
id int PRIMARY KEY AUTO_INCREMENT,
name varchar(250) NOT NULL,
itemKindID int NOT NULL,
FOREIGN KEY (itemKindID) REFERENCES ItemKind(id)
);
Insert into ItemType (name,itemKindID) VALUES
("Go Energia ital",1),
("Kobra Energia ital",1),
("Szikkadt rántott húsos zsemle",1),
("Hamburgeres popcorn",1),
("Automatás zsákbamacska kávé",3),
("Automatás zsákbamacska kávé",4),
("Tanár telefonszáma",2);

Create table Item(
id int PRIMARY KEY AUTO_INCREMENT,
value int NOT NULL,
isItUsed tinyint NOT NULL,
typeID int NOT NULL,
classroomID int NOT NULL,
FOREIGN KEY (classroomID) References Classroom(id),
FOREIGN KEY (typeID) REFERENCES ItemType(id)
);
Insert into Item (value,isItUsed,typeID,classroomID) VALUES
(5,0,1,3),
(10,0,2,3),
(15,0,3,3),
(20,0,4,3),
(1,0,5,3),
(1,0,6,3),
(5,0,1,8),
(10,0,2,6),
(1,0,7,6),
(5,0,1,7),
(20,0,4,11),
(1,0,5,12),
(15,0,3,13);