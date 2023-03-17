package gest.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import gest.enums.DBCrendecial;

public final class DB {

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    DBCrendecial.URL.code(),
                    DBCrendecial.USER.code(),
                    DBCrendecial.PASSWORD.code());
        } catch (SQLException e) {
            return null;
        }
    }
}
