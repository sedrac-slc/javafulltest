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
import gest.entity.Person;

public class PersonJdbcDB implements CrudDB<Person> {

    @Override
    public boolean add(Person person) {
        String sql = "SELECT * FROM person LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.execute();
            jst.moveToInsertRow();
            jst.updateString("firstName", person.getFirstName());
            jst.updateString("lastName", person.getLastName());
            jst.updateString("birthday", person.getBirthday().toString());
            jst.updateString("gender", person.getGender().name());
            jst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            jst.insertRow();
            jst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Person> find(UUID id) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        Person person = new Person();
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, id.toString());
            jst.execute();
            jst.next();
            person = new Person(
                    jst.getString("uuid"),
                    jst.getString("firstName"),
                    jst.getString("lastName"),
                    jst.getString("birthday"),
                    jst.getString("gender"),
                    jst.getString("created_at"),
                    jst.getString("updated_at"));
            return person.getId() == null ? Optional.empty() : Optional.of(person);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.execute();
            while (jst.next())
                persons.add(new Person(
                        jst.getString("uuid"),
                        jst.getString("firstName"),
                        jst.getString("lastName"),
                        jst.getString("birthday"),
                        jst.getString("gender"),
                        jst.getString("created_at"),
                        jst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return persons;
    }

    @Override
    public boolean delete(Person person) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, person.getId().toString());
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
        return delete(new Person(id));
    }

    @Override
    public boolean update(Person person) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, person.getId().toString());
            jst.execute();
            jst.next();
            jst.updateString("firstName", person.getFirstName());
            jst.updateString("lastName", person.getLastName());
            jst.updateString("birthday", person.getBirthday().toString());
            jst.updateString("gender", person.getGender().name());
            jst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            jst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
