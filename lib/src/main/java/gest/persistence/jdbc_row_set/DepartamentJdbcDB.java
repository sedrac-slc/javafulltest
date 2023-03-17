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
import gest.entity.Departament;

public class DepartamentJdbcDB implements CrudDB<Departament> {

    @Override
    public boolean add(Departament departament) {
        String sql = "SELECT * FROM departament LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.execute();
            jst.moveToInsertRow();
            jst.updateString("name", departament.getName());
            jst.updateString("created_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            jst.insertRow();
            jst.beforeFirst();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Departament> find(UUID id) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        Departament departament = new Departament();
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, id.toString());
            jst.execute();
            jst.next();
            departament = new Departament(
                    jst.getString("uuid"),
                    jst.getString("name"),
                    jst.getString("created_at"),
                    jst.getString("updated_at"));
            return departament.getId() == null ? Optional.empty() : Optional.of(departament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Departament> findAll() {
        String sql = "SELECT * FROM departament";
        List<Departament> departaments = new ArrayList<>();
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.execute();
            while (jst.next())
                departaments.add(new Departament(
                        jst.getString("uuid"),
                        jst.getString("name"),
                        jst.getString("created_at"),
                        jst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return departaments;
    }

    @Override
    public boolean delete(Departament departament) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, departament.getId().toString());
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
        return delete(new Departament(id));
    }

    @Override
    public boolean update(Departament departament) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        try (JdbcRowSet jst = DBRowSet.openJdbc()) {
            jst.setCommand(sql);
            jst.setString(1, departament.getId().toString());
            jst.execute();
            jst.next();
            jst.updateString("name", departament.getName());
            jst.updateString("updated_at", LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            jst.updateRow();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
