package gest.persistence.join_row_set;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;

import gest.connection.DBRowSet;

public class PersonDepartamentJoinDB {

    public void findAll() {
        try (
                CachedRowSet personJdbc = openWithSql("SELECT * FROM person");
                CachedRowSet departamentJdbc = openWithSql("SELECT * FROM departament");
                CachedRowSet personDepartamentJdbc = openWithSql("SELECT * FROM person_departament");
                JoinRowSet join = DBRowSet.openJoin()) {

            // personJdbc.execute();
            departamentJdbc.execute();
            personDepartamentJdbc.execute();

            // join.addRowSet(personJdbc, "uuid");
            join.addRowSet(departamentJdbc, "uuid");
            join.addRowSet(personDepartamentJdbc, "departament_uuid");
            // join.setJoinType(JoinRowSet.INNER_JOIN);

            while (join.next()) {
                System.out.print(join.getString("name") + " - ");
                // System.out.print(join.getString("departament_uuid") + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CachedRowSet openWithSql(String sql) throws SQLException {
        CachedRowSet jst = DBRowSet.openCached();
        jst.setCommand(sql);
        return jst;
    }

}
