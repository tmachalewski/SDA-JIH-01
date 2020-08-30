package pl.sda;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cinema?serverTimezone=Europe/Warsaw";
    //private static final String DB_URL =  "jdbc:h2:~/test2";

    private static final String USER = "root";
//    private static final String USER = "sa";
    private static final String PASSWORD = "12345";
//    private static final String PASSWORD = "";


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
            PreparedStatement ps = con.prepareStatement("Select * from actor where actor_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
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



    public static void getActorWithNameAndSurnameMatchingPattern(MysqlDataSource ds, String namePattern, String surnamePattern){
        try (Connection con = ds.getConnection()){
            PreparedStatement ps = con.prepareStatement("Select * from actor where first_name like ? and last_name like ?");
            ps.setString(1, namePattern);
            ps.setString(2, surnamePattern);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.print(rs.getInt("actor_id")+" ");
                System.out.print(rs.getString(2)+" ");
                System.out.print(rs.getString(3)+" ");
                System.out.println(rs.getString(4));
            }
            System.out.println("--------------------");
            ps.setString(1, surnamePattern);
            ps.setString(2, namePattern);
            rs = ps.executeQuery();
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

    public static void deleteTableMovies(DataSource ds)
    {
        try(Connection con = ds.getConnection()) {
            Statement stmt = con.createStatement();
            stmt.execute("Drop table if exists movies");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableMovies(DataSource ds)
    {
        try(Connection con = ds.getConnection()) {
            //connection
            Statement stmt = con.createStatement();
            stmt.execute("create table if not exists movies("+
                    "id INTEGER primary key auto_increment,"+
                    "title VARCHAR(45)"+
                    ")"
                    );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editMovie(DataSource ds, int id, String newTitle){
        try(Connection con = ds.getConnection()){
            PreparedStatement prst = con.prepareStatement("Update movies set title=? where id=?");
            prst.setInt(2, id);
            prst.setString(1, newTitle);
            prst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public static void insertIntoMovies(DataSource ds)
    {
        try(Connection con = ds.getConnection()) {
            //connection
            Statement stmt = con.createStatement();
            String insert1 = "Insert into movies(title) values ('Film1')";
            String insert2 = "Insert into movies(title) values ('Film2')";
            String insert3 = "Insert into movies(title) values ('Film3')";
            stmt.execute(insert1);
            stmt.execute(insert2);
            stmt.execute(insert3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void selectAndDelete(DataSource ds, int id)
    {
        try(Connection con = ds.getConnection()) {
            //connection
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from movies where id="+id);
            while(rs.next())
            {
                System.out.println(rs.getInt("id")+" "+rs.getString("title"));
            };

            stmt.executeUpdate("Delete from movies where id="+id);

            rs = stmt.executeQuery("Select * from movies");
            while(rs.next())
            {
                System.out.println(rs.getInt("id")+" "+rs.getString("title"));
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFromMoviesFilmWithId(DataSource ds, int id)
    {
        try(Connection con = ds.getConnection()) {
            //connection
            PreparedStatement stmt = con.prepareStatement("Delete from movies where id=?");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void switchRecord1And3(DataSource ds)
    {
        try(Connection con = ds.getConnection()) {
            //connection
            CallableStatement stmt = con.prepareCall("{call SwitchFilm1And3()}");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void switchRecord1And3WithParameters(DataSource ds, int id1, int id2)
    {
        try(Connection con = ds.getConnection()) {
            //connection
            CallableStatement stmt = con.prepareCall("{call SwitchFilm1And3WithParameter(?,?)}");
            stmt.setInt(1,id1);
            stmt.setInt(2,id2);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws ClassNotFoundException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
        MysqlDataSource ds = new MysqlDataSource();
        //JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl(DB_URL);
        ds.setUser(USER);
        ds.setPassword(PASSWORD);

//        try {
//            DriverManager.getConnection(DB_URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        //returnAllActors(ds);
        //getActorWithId(ds, 3);
        //getActorWithNameAndSurnameMatchingPattern(ds, "%b%", "%a%");
        deleteTableMovies(ds);
        createTableMovies(ds);
        insertIntoMovies(ds);
        //editMovie(ds, 2, "Titanic");
        selectAndDelete(ds, 2);

        //deleteFromMoviesFilmWithId(ds, 2);
//        switchRecord1And3(ds);
        //switchRecord1And3WithParameters(ds, 3, 1);
    }
}
