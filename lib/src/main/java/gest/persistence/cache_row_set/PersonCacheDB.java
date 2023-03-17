package gest.persistence.cache_row_set;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.rowset.CachedRowSet;

import gest.interfaces.CrudDB;
import gest.util.ConstantDateFormat;
import gest.connection.DBRowSet;
import gest.entity.Person;

public class PersonCacheDB implements CrudDB<Person> {

    @Override
    public boolean add(Person person) {
        String sql = "SELECT * FROM person LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.execute();
            cst.moveToInsertRow();
            cst.updateString("firstName", person.getFirstName());
            cst.updateString("lastName", person.getLastName());
            cst.updateString("birthday", person.getBirthday().toString());
            cst.updateString("gender", person.getGender().name());
            cst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            cst.insertRow();
            cst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Person> find(UUID id) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        Person person = new Person();
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, id.toString());
            cst.execute();
            cst.next();
            person = new Person(
                    cst.getString("uuid"),
                    cst.getString("firstName"),
                    cst.getString("lastName"),
                    cst.getString("birthday"),
                    cst.getString("gender"),
                    cst.getString("created_at"),
                    cst.getString("updated_at"));
            return person.getId() == null ? Optional.empty() : Optional.of(person);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.execute();
            new java.text.SimpleDateFormat();
            while (cst.next())
                persons.add(new Person(
                        cst.getString("uuid"),
                        cst.getString("firstName"),
                        cst.getString("lastName"),
                        cst.getString("birthday"),
                        cst.getString("gender"),
                        cst.getString("created_at").replace(".0", ""),
                        cst.getString("updated_at").replace(".0", "")));

        } catch (SQLException e) {
            return List.of();
        }
        return persons;
    }

    @Override
    public boolean delete(Person person) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, person.getId().toString());
            cst.execute();
            cst.next();
            cst.deleteRow();
            cst.beforeFirst();
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
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, person.getId().toString());
            cst.execute();
            cst.next();
            cst.updateString("firstName", person.getFirstName());
            cst.updateString("lastName", person.getLastName());
            cst.updateString("birthday", person.getBirthday().toString());
            cst.updateString("gender", person.getGender().name());
            cst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            cst.updateRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
