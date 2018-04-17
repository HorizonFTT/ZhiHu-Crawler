package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Database
 */
public class Database {

    private Connection connection = null;

    private Statement statement = null;

    private ResultSet resultSet = null;

    private boolean local;

    public Database(boolean local){
        this.local = local;
        connect();
    }

    private void connect() {
        String connectionString = null;
        String sql = null;
        if (local == true) {
            connectionString = "jdbc:sqlserver://localhost:1433;integratedSecurity=true;databaseName=SpiderTest";
            sql = "IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='ZhiHuUser')"
                    + "CREATE TABLE dbo.ZhiHuUser (ID nvarchar(128) PRIMARY KEY NOT NULL, Name nvarchar(32) NOT NULL, "
                    + "Sex nvarchar(6) NOT NULL, Introduction nvarchar(600) NULL, School nvarchar(100) NULL, "
                    + "Company nvarchar(100) NULL, Job nvarchar(100) NULL, Business nvarchar(32) NULL, "
                    + "Location nvarchar(100) NULL, Answer int NOT NULL, Agree int NOT NULL, Follower int NOT NULL);";
        } else {
            connectionString = "jdbc:mysql://120.77.214.52:3306/SpiderTest?useSSL=false&user=root&password=159357zxcZXC?";
            sql = "CREATE TABLE IF NOT EXISTS ZhiHuUser (ID nvarchar(128) PRIMARY KEY NOT NULL, Name nvarchar(32) NOT NULL, "
                    + "Sex nvarchar(6) NOT NULL, Introduction nvarchar(600) NULL, School nvarchar(100) NULL, "
                    + "Company nvarchar(100) NULL, Job nvarchar(100) NULL, Business nvarchar(32) NULL, "
                    + "Location nvarchar(100) NULL, Answer int NOT NULL, Agree int NOT NULL, Follower int NOT NULL);";
        }
        try {
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Database has been connected.");
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void check(){
        try {
            if(connection.isClosed()){
                System.out.println("oops!");
                connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(User user) {
        try {
            check();
            var sql = "INSERT INTO ZhiHuUser (ID, Name, Sex, Introduction, School, Company, Job, Business, "
                    + "Location, Answer, Agree, Follower) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getIntroduction());
            ps.setString(5, user.getSchool());
            ps.setString(6, user.getCompany());
            ps.setString(7, user.getJob());
            ps.setString(8, user.getBusiness());
            ps.setString(9, user.getLocation());
            ps.setInt(10, user.getAnswer());
            ps.setInt(11, user.getAgree());
            ps.setInt(12, user.getFollower());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void num() {
        try {
            check();
            var sql = "SELECT COUNT(ID) AS Num FROM ZhiHuUser;";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("Total amount of data: " + resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            check();
            statement = connection.createStatement();
            var sql = "DELETE FROM ZhiHuUser;";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}