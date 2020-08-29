package pl.sda;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sakila?serverTimezone=Europe/Warsaw";

    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static void returnAllActors(MysqlDataSource ds){
        try (Connection con = ds.getConnection()){
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM actor LIMIT 3");
            while(rs.next()){
                System.out.print(rs.getInt("actor_id")+" ");
                System.out.print(rs.getString(2)+" ");
                System.out.print(rs.getString(3)+" ");
                System.out.println(rs.getString(4));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getActorWithId(MysqlDataSource ds, int id){
        try (Connection con = ds.getConnection()){
            //con.prepareStatement();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl(DB_URL);
        ds.setUser(USER);
        ds.setPassword(PASSWORD);

        returnAllActors(ds);



    }
}
