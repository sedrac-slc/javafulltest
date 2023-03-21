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
import gest.entity.PersonDepartament;

public class PersonDepartamentCacheDB implements CrudDB<PersonDepartament> {

    @Override
    public boolean add(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.execute();
            cst.moveToInsertRow();
            cst.updateString("person_uuid", personDepartament.getPerson().getId().toString());
            cst.updateString("departament_uuid", personDepartament.getDepartament().getId().toString());
            cst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            cst.insertRow();
            cst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<PersonDepartament> find(UUID id) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        PersonDepartament personDepartament = new PersonDepartament();
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, id.toString());
            cst.execute();
            cst.next();
            personDepartament = new PersonDepartament(
                    cst.getString("uuid"),
                    cst.getString("person_uuid"),
                    cst.getString("departament_uuid"),
                    cst.getString("created_at"),
                    cst.getString("updated_at"));
            return personDepartament.getId() == null ? Optional.empty() : Optional.of(personDepartament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonDepartament> findAll() {
        String sql = "SELECT * FROM person_departament";
        List<PersonDepartament> personDepartaments = new ArrayList<>();
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.execute();
            while (cst.next())
                personDepartaments.add(new PersonDepartament(
                        cst.getString("uuid"),
                        cst.getString("person_uuid"),
                        cst.getString("departament_uuid"),
                        cst.getString("created_at"),
                        cst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return personDepartaments;
    }

    @Override
    public boolean delete(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, personDepartament.getId().toString());
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
        return delete(new PersonDepartament(id));
    }

    @Override
    public boolean update(PersonDepartament personDepartament) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, personDepartament.getId().toString());
            cst.execute();
            cst.next();
            cst.updateString("person_uuid", personDepartament.getPerson().getId().toString());
            cst.updateString("departament_uuid", personDepartament.getDepartament().getId().toString());
            cst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            cst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
