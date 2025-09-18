package lk.acpt.javaee.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection; //why private
    //to store the only one DBConnection object

    private final Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        //one time ----> 1st run

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_system", "root", "2245");

    }

    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
        if(dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }

}
