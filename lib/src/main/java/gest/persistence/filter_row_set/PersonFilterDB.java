package gest.persistence.filter_row_set;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

import gest.interfaces.CrudFilterDB;
import gest.util.ConstantDateFormat;
import gest.connection.DBRowSet;
import gest.entity.Person;

public class PersonFilterDB implements CrudFilterDB<Person> {

    @Override
    public boolean add(Person person) {
        String sql = "SELECT * FROM person LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            fst.moveToInsertRow();
            fst.updateString("firstName", person.getFirstName());
            fst.updateString("lastName", person.getLastName());
            fst.updateString("birthday", person.getBirthday().toString());
            fst.updateString("gender", person.getGender().name());
            fst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            fst.insertRow();
            fst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Person> find(UUID id) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        Person person = new Person();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, id.toString());
            fst.execute();
            fst.next();
            person = new Person(
                    fst.getString("uuid"),
                    fst.getString("firstName"),
                    fst.getString("lastName"),
                    fst.getString("birthday"),
                    fst.getString("gender"),
                    fst.getString("created_at"),
                    fst.getString("updated_at"));
            return person.getId() == null ? Optional.empty() : Optional.of(person);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            while (fst.next())
                persons.add(new Person(
                        fst.getString("uuid"),
                        fst.getString("firstName"),
                        fst.getString("lastName"),
                        fst.getString("birthday"),
                        fst.getString("gender"),
                        fst.getString("created_at"),
                        fst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return persons;
    }

    @Override
    public List<Person> findAll(Predicate p) {
        String sql = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            fst.setFilter(p);
            while (fst.next())
                persons.add(new Person(
                        fst.getString("uuid"),
                        fst.getString("firstName"),
                        fst.getString("lastName"),
                        fst.getString("birthday"),
                        fst.getString("gender"),
                        fst.getString("created_at"),
                        fst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return persons;
    }

    @Override
    public boolean delete(Person person) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1)";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, person.getId().toString());
            fst.execute();
            fst.next();
            fst.deleteRow();
            fst.beforeFirst();
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
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, person.getId().toString());
            fst.execute();
            fst.next();
            fst.updateString("firstName", person.getFirstName());
            fst.updateString("lastName", person.getLastName());
            fst.updateString("birthday", person.getBirthday().toString());
            fst.updateString("gender", person.getGender().name());
            fst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            fst.updateRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
