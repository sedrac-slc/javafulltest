package gest.persistence.jdbc_row_set;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.rowset.JdbcRowSet;

import gest.interfaces.CrudDB;
import gest.util.ConstantDateFormat;
import gest.connection.DBRowSet;
import gest.entity.PersonDepartament;

public class PersonDepartamentJdbcDB implements CrudDB<PersonDepartament> {

    @Override
    public boolean add(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.execute();
            jst.moveToInsertRow();
            jst.updateString("person_uuid", personDepartament.getPerson().getId().toString());
            jst.updateString("departament_uuid", personDepartament.getDepartament().getId().toString());
            jst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            jst.insertRow();
            jst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<PersonDepartament> find(UUID id) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        PersonDepartament personDepartament = new PersonDepartament();
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, id.toString());
            jst.execute();
            jst.next();
            personDepartament = new PersonDepartament(
                    jst.getString("uuid"),
                    jst.getString("person_uuid"),
                    jst.getString("departament_uuid"),
                    jst.getString("created_at"),
                    jst.getString("updated_at"));
            return personDepartament.getId() == null ? Optional.empty() : Optional.of(personDepartament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonDepartament> findAll() {
        String sql = "SELECT * FROM person_departament";
        List<PersonDepartament> personDepartaments = new ArrayList<>();
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.execute();
            while (jst.next())
                personDepartaments.add(new PersonDepartament(
                        jst.getString("uuid"),
                        jst.getString("person_uuid"),
                        jst.getString("departament_uuid"),
                        jst.getString("created_at"),
                        jst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return personDepartaments;
    }

    @Override
    public boolean delete(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, personDepartament.getId().toString());
            jst.execute();
            jst.next();
            jst.deleteRow();
            jst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        return delete(new PersonDepartament(id));
    }

    @Override
    public boolean update(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, personDepartament.getId().toString());
            jst.execute();
            jst.next();
            jst.updateString("person_uuid", personDepartament.getPerson().getId().toString());
            jst.updateString("departament_uuid", personDepartament.getDepartament().getId().toString());
            jst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            jst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
