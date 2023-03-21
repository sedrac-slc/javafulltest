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
import gest.entity.PersonDepartament;

public class PersonDepartamentFilterDB implements CrudFilterDB<PersonDepartament> {

    @Override
    public boolean add(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            fst.moveToInsertRow();
            fst.updateString("person_uuid", personDepartament.getPerson().getId().toString());
            fst.updateString("departament_uuid", personDepartament.getDepartament().getId().toString());
            fst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            fst.insertRow();
            fst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<PersonDepartament> find(UUID id) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        PersonDepartament personDepartament = new PersonDepartament();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, id.toString());
            fst.execute();
            fst.next();
            personDepartament = new PersonDepartament(
                    fst.getString("uuid"),
                    fst.getString("person_uuid"),
                    fst.getString("departament_uuid"),
                    fst.getString("created_at"),
                    fst.getString("updated_at"));
            return personDepartament.getId() == null ? Optional.empty() : Optional.of(personDepartament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonDepartament> findAll() {
        String sql = "SELECT * FROM person_departament";
        List<PersonDepartament> personDepartaments = new ArrayList<>();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            while (fst.next())
                personDepartaments.add(new PersonDepartament(
                        fst.getString("uuid"),
                        fst.getString("person_uuid"),
                        fst.getString("departament_uuid"),
                        fst.getString("created_at"),
                        fst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return personDepartaments;
    }

    @Override
    public List<PersonDepartament> findAll(Predicate p) {
        String sql = "SELECT * FROM person_departament";
        List<PersonDepartament> personDepartaments = new ArrayList<>();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            fst.setFilter(p);
            while (fst.next())
                personDepartaments.add(new PersonDepartament(
                        fst.getString("uuid"),
                        fst.getString("person_uuid"),
                        fst.getString("departament_uuid"),
                        fst.getString("created_at"),
                        fst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return personDepartaments;
    }

    @Override
    public boolean delete(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, personDepartament.getId().toString());
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
        return delete(new PersonDepartament(id));
    }

    @Override
    public boolean update(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, personDepartament.getId().toString());
            fst.execute();
            fst.next();
            fst.updateString("person_uuid", personDepartament.getPerson().getId().toString());
            fst.updateString("departament_uuid", personDepartament.getDepartament().getId().toString());
            fst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            fst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
