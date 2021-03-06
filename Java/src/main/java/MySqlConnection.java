import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MySqlConnection {

    private Connection connection;

    /**
     * letrehozza a kapcsolatot az adatbazissal, amennyiben megfeleloek az adatok es letezik az adatbazis
     * @return az adatbazis letezeset adja vissza
     */
    public boolean makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/escapefromneu", "root", "");
            if (connection != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * itt kerjuk le a valaszokat a kerdesek alapjan
     * @param questionId a kerdesnek az azonositoja
     * @return a valaszok
     * @throws SQLException
     */
    private HashMap<String, Boolean> getAnswersByQuestionId(int questionId) throws SQLException {
        HashMap<String, Boolean> tempAnswers = new HashMap<String, Boolean>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM answer WHERE questionID=?");
        stmt.setInt(1, questionId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            tempAnswers.put(rs.getString("answer"), rs.getInt("isItCorrect") == 1 ? true : false);
        }
        return tempAnswers;
    }

    /**
     * a tantargyhoz tartozo kerdeseket itt kerjuk le
     * @param subjectId a tantargy azonositoja
     * @return kerdeseket adja vissza
     * @throws SQLException
     */
    private ArrayList<Question> getQuestionsBySubjectId(int subjectId) throws SQLException {
        ArrayList<Question> tempQuestions = new ArrayList<Question>();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM question WHERE subjectID=?");
        stmt.setInt(1, subjectId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            HashMap<String, Boolean> tempAnswers = getAnswersByQuestionId(rs.getInt("id"));
            tempQuestions.add(new Question(rs.getInt("id"), rs.getString("question"), tempAnswers));
        }
        return tempQuestions;
    }

    /**
     * a tanarpokhoz tartozo tantargyat kerjuk le
     * @param teacherID tanar azonositoja
     * @return tantargy osztalyba lementjuk a tantar tantargyat
     * @throws SQLException
     */
    private Subject getSubjectByTeacherId(int teacherID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM teachersubject WHERE teacherID=?");
        stmt.setInt(1, teacherID);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        ArrayList<Question> tempQuestions = getQuestionsBySubjectId(rs.getInt("id"));
        return new Subject(rs.getInt("id"), rs.getString("name"), tempQuestions);
    }

    /**
     * tanterem szerint lekerjuk a tanart
     * @param classroomId tanterem azonositoja
     * @return tanar osztalyba lemenjuk a tanarokat
     * @throws SQLException
     */
    public Teacher getTeacherByClassroomId(int classroomId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM teacher WHERE classroomID=?");
        stmt.setInt(1, classroomId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Subject tempSubject = null;
        try {
            tempSubject = getSubjectByTeacherId(rs.getInt("id"));
        } catch (SQLException e) {

        }
        try {
            return new Teacher(rs.getInt("id"), rs.getString("name"), tempSubject);
        } catch (SQLException e) {

        }
        return null;
    }

    /**
     * itt szedjuk ki a kincseket a tantermek alapjan
     * @param classroomId tanterem azonositoja
     * @return kincseket adja vissza
     * @throws SQLException
     */
    public ArrayList<Treasure> getTreasuresByClassroomId(int classroomId) throws SQLException {
        String sql = "SELECT t.id, tt.name, tt.value FROM treasure t INNER JOIN treasuretype tt on tt.id=t.treasureTypeID WHERE classroomID=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, classroomId);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Treasure> tempTreasures = new ArrayList<Treasure>();
        while (rs.next()) {
            Treasure tempTreasure = new Treasure(rs.getInt("id"), rs.getString("name"), rs.getInt("value"));
            tempTreasures.add(tempTreasure);
        }
        return tempTreasures;
    }

    /**
     * az itemek tipusokat kerjuk le tipus id alapjan
     * @param itemTypeId itemek tipusa
     * @return az item tpiusok
     * @throws SQLException
     */
    public ItemType getItemTypeByItemTypeId(int itemTypeId) throws SQLException {
        String sql = "SELECT it.id, it.name type_name, ik.id ik_id, ik.name kind FROM itemtype it INNER JOIN itemkind ik ON it.itemKindID=ik.id WHERE it.id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, itemTypeId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Enums.ItemKind tempItemKind;
        switch (rs.getInt("ik_id")) {
            case 1:
                tempItemKind = Enums.ItemKind.SELFCONFIDENCE;
                break;
            case 2:
                tempItemKind = Enums.ItemKind.QUESTIONSKIPPER;
                break;
            case 3:
                tempItemKind = Enums.ItemKind.GOODANSWER;
                break;
            case 4:
                tempItemKind = Enums.ItemKind.WRONGANSWER;
                break;
            default:
                tempItemKind = Enums.ItemKind.GOODANSWER;
                break;
        }
        return new ItemType(rs.getInt("id"), rs.getString("type_name"), tempItemKind);
    }

    /***
     * a fogyaszthato itemeket kerjuk le az osztalyok azonositoja alapjan
     * @param classroomId tanterem azonositoja
     * @return itemeket adja vissza
     * @throws SQLException
     */
    public ArrayList<Item> getItemsByClassroomId(int classroomId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM item WHERE classroomID=?");
        stmt.setInt(1, classroomId);
        ResultSet rs = stmt.executeQuery();
        ArrayList<Item> tempItems = new ArrayList<Item>();
        while (rs.next()) {
            ItemType tempType = getItemTypeByItemTypeId(rs.getInt("typeID"));
            Item tempItem = new Item(rs.getInt("id"), tempType.getName(), tempType, rs.getInt("value"));
            tempItems.add(tempItem);
        }
        return tempItems;
    }

    /**
     * lekerjuk a tantermeket tanterem azonosito alapjan
     * @param classroomId tanterem azonositoja
     * @return tantermek
     * @throws SQLException
     */
    public Classroom getClassRoomById(int classroomId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM classroom WHERE id=?");
        stmt.setInt(1,classroomId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        Teacher tempTeacher = getTeacherByClassroomId(classroomId);
        ArrayList<Treasure> tempTreasures = getTreasuresByClassroomId(classroomId);
        ArrayList<Item> tempItems = getItemsByClassroomId(classroomId);
        HashMap<String,Integer> nextrooms = new HashMap<String, Integer>();
        String nextsFull = rs.getString("nextrooms");
        String[] nexts = nextsFull.split(";");
        for (String s: nexts) {
            nextrooms.put(getClassroomNameById(Integer.parseInt(s)),Integer.parseInt(s));
        }
        return new Classroom(classroomId, rs.getString("roomname"), tempTeacher, tempTreasures, tempItems,nextrooms);
    }

    /**
     * tanterm nevet kerjuk le az azonositoja alapjan
     * @param classroomId tanterem azonositoja
     * @return tanterem neve
     * @throws SQLException
     */
    public String getClassroomNameById(int classroomId) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement("SELECT roomname FROM classroom WHERE id=?");
        stmt.setInt(1,classroomId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString("roomname");
    }

    /**
     * a tablak sorainak szamat kerjuk le
     * @param tablename a tabla neve
     * @return sorok szama
     */
    public int countOfRows(String tablename){
        int count=0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement("SELECT COUNT(1) AS numberofcolumns FROM"+" "+tablename);
            rs = stmt.executeQuery();
            rs.next();
            count=rs.getInt("numberofcolumns");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * lekerjuk a bufenel kapott itemet
     * @param ItemTypeid item tipusa
     * @return item, amit kapott a jatekos
     * @throws SQLException
     */
    public ItemType getOneItemType(int ItemTypeid) throws SQLException {
        String sql = "SELECT it.id, it.name type_name, ik.id ik_id, ik.name kind FROM itemtype it INNER JOIN itemkind ik ON it.itemKindID=ik.id Where it.id=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, ItemTypeid);
        ResultSet rs = stmt.executeQuery();
        ItemType temp = null;
        while (rs.next()) {
            Enums.ItemKind tempItemKind;
            switch (rs.getInt("ik_id")) {
                case 1:
                    tempItemKind = Enums.ItemKind.SELFCONFIDENCE;
                    break;
                case 2:
                    tempItemKind = Enums.ItemKind.QUESTIONSKIPPER;
                    break;
                case 3:
                    tempItemKind = Enums.ItemKind.GOODANSWER;
                    break;
                case 4:
                    tempItemKind = Enums.ItemKind.WRONGANSWER;
                    break;
                default:
                    tempItemKind = Enums.ItemKind.GOODANSWER;
                    break;
            }
            temp = new ItemType(rs.getInt("id"), rs.getString("type_name"), tempItemKind);
        }
        return temp;
    }

}


