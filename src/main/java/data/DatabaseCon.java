package data;

import java.sql.Connection;
import java.sql.Statement;

public interface DatabaseCon {

    Statement getStatement();

    Connection getConnection();

    void closeConnection();

    String getUserConnection();

    String getURLConnection();

    String getPasswordConnection();
}
