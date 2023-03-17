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
import gest.entity.Departament;

public class DepartamentFilterDB implements CrudFilterDB<Departament> {

    @Override
    public boolean add(Departament departament) {
        String sql = "SELECT * FROM departament LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            fst.moveToInsertRow();
            fst.updateString("name", departament.getName());
            fst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            fst.insertRow();
            fst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Departament> find(UUID id) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        Departament departament = new Departament();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, id.toString());
            fst.execute();
            fst.next();
            departament = new Departament(
                    fst.getString("uuid"),
                    fst.getString("name"),
                    fst.getString("created_at"),
                    fst.getString("updated_at"));
            return departament.getId() == null ? Optional.empty() : Optional.of(departament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Departament> findAll() {
        String sql = "SELECT * FROM departament";
        List<Departament> departaments = new ArrayList<>();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            while (fst.next())
                departaments.add(new Departament(
                        fst.getString("uuid"),
                        fst.getString("name"),
                        fst.getString("created_at"),
                        fst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return departaments;
    }

    @Override
    public List<Departament> findAll(Predicate p) {
        String sql = "SELECT * FROM departament";
        List<Departament> departaments = new ArrayList<>();
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.execute();
            fst.setFilter(p);
            while (fst.next())
                departaments.add(new Departament(
                        fst.getString("uuid"),
                        fst.getString("name"),
                        fst.getString("created_at"),
                        fst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return departaments;
    }

    @Override
    public boolean delete(Departament departament) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, departament.getId().toString());
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
        return delete(new Departament(id));
    }

    @Override
    public boolean update(Departament departament) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        try (FilteredRowSet fst = DBRowSet.openFilter()) {
            fst.setCommand(sql);
            fst.setString(1, departament.getId().toString());
            fst.execute();
            fst.next();
            fst.updateString("name", departament.getName());
            fst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            fst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
