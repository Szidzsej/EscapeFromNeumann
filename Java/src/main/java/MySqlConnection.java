import java.sql.*;
public class MySqlConnection {

    public static void main(String args[]){
      try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/EFN","root","");
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("select * from emp");
        while(rs.next())
         con.close();
      }catch(Exception e){ System.out.println(e);}
    }
}
