package Database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Database
 */
public class Database {

    private Connection connection = null;

    private Statement statement = null;

    private ResultSet resultSet = null;

    private boolean local;

    public Database(boolean local) {
        this.local = local;
        connect();
    }

    private void connect() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/jdbc.properties"));//从配置文件中读取信息
            var address = properties.getProperty("Address");
            var user = properties.getProperty("User");
            var password = properties.getProperty("Password");
            var databaseName = properties.getProperty("DatabaseName");
            String connectionString = null;
            String sql = null;

            //若表不存在,建立,默认表名为ZhiHuUser
            if (local == true) {
                connectionString = "jdbc:sqlserver://" + address + ";integratedSecurity=true;databaseName="
                        + databaseName;
                sql = "IF NOT EXISTS(SELECT * FROM sysobjects WHERE name='ZhiHuUser')"
                        + "CREATE TABLE dbo.ZhiHuUser (ID nvarchar(128) PRIMARY KEY NOT NULL, Name nvarchar(32) NOT NULL, "
                        + "Sex nvarchar(6) NOT NULL, Introduction nvarchar(600) NULL, School nvarchar(100) NULL, "
                        + "Company nvarchar(100) NULL, Job nvarchar(100) NULL, Business nvarchar(32) NULL, "
                        + "Location nvarchar(100) NULL, Answer int NOT NULL, Agree int NOT NULL, Follower int NOT NULL);";
            } else {
                connectionString = "jdbc:mysql://" + address + "/" + databaseName + "?useSSL=false&user=" + user
                        + "&password=" + password;
                sql = "CREATE TABLE IF NOT EXISTS ZhiHuUser (ID nvarchar(128) PRIMARY KEY NOT NULL, Name nvarchar(32) NOT NULL, "
                        + "Sex nvarchar(6) NOT NULL, Introduction nvarchar(600) NULL, School nvarchar(100) NULL, "
                        + "Company nvarchar(100) NULL, Job nvarchar(100) NULL, Business nvarchar(32) NULL, "
                        + "Location nvarchar(100) NULL, Answer int NOT NULL, Agree int NOT NULL, Follower int NOT NULL);";
            }

            connection = DriverManager.getConnection(connectionString);
            System.out.println("Database has been connected.");
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(User user) {
        try {
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
            statement = connection.createStatement();
            var sql = "DELETE FROM ZhiHuUser;";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keepAlive() {
        try {
            statement = connection.createStatement();
            var sql = "SELECT 1;";
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}