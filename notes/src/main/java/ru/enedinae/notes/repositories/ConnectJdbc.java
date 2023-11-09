package ru.enedinae.notes.repositories;

import java.sql.*;

public class ConnectJdbc {
    private static final String URL = "jdbc:postgresql://localhost:5432/notes";
    private static final String USER = "daivashnev";
    private static final String PASSWORD = "284694";

    public void createConnect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM notes");

            while(rs.next()) {
                System.out.println(rs.getString("id") + " "
                        + rs.getString("name") + " "
                        + rs.getString("description") + " "
                        + rs.getString("deadline"));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
