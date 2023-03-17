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
import gest.entity.Departament;

public class DepartamentCacheDB implements CrudDB<Departament> {

    @Override
    public boolean add(Departament departament) {
        String sql = "SELECT * FROM departament LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.execute();
            cst.moveToInsertRow();
            cst.updateString("name", departament.getName());
            cst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            cst.insertRow();
            cst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Departament> find(UUID id) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        Departament departament = new Departament();
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, id.toString());
            cst.execute();
            cst.next();
            departament = new Departament(
                    cst.getString("uuid"),
                    cst.getString("name"),
                    cst.getString("created_at"),
                    cst.getString("updated_at"));
            return departament.getId() == null ? Optional.empty() : Optional.of(departament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Departament> findAll() {
        String sql = "SELECT * FROM departament";
        List<Departament> departaments = new ArrayList<>();
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.execute();
            while (cst.next())
                departaments.add(new Departament(
                        cst.getString("uuid"),
                        cst.getString("name"),
                        cst.getString("created_at"),
                        cst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return departaments;
    }

    @Override
    public boolean delete(Departament departament) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, departament.getId().toString());
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
        return delete(new Departament(id));
    }

    @Override
    public boolean update(Departament departament) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        try (CachedRowSet cst = DBRowSet.openCached()) {
            cst.setCommand(sql);
            cst.setString(1, departament.getId().toString());
            cst.execute();
            cst.next();
            cst.updateString("name", departament.getName());
            cst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            cst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
