package dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionFactory {

    public static Connection getMysqlConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/double_color");
        conn.setAutoCommit(false);
        return conn;
    }
}
