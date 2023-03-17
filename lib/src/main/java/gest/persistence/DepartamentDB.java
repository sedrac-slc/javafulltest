package gest.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import gest.interfaces.CrudDB;
import gest.util.ConstantDateFormat;
import gest.connection.DB;
import gest.entity.Departament;

public class DepartamentDB implements CrudDB<Departament> {

    @Override
    public boolean add(Departament departament) {
        String sql = "INSERT INTO departament(name) VALUES (?)";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, departament.getName());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Departament> find(UUID id) {
        String sql = "SELECT * FROM departament WHERE uuid=? LIMIT 1";
        Departament departament = new Departament();
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id.toString());
            ResultSet rst = pst.executeQuery();
            while (rst.next())
                departament = new Departament(
                        rst.getString("uuid"),
                        rst.getString("name"),
                        rst.getString("created_at"),
                        rst.getString("updated_at"));
            rst.close();
            return departament.getId() == null ? Optional.empty() : Optional.of(departament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Departament> findAll() {
        String sql = "SELECT * FROM departament";
        List<Departament> departaments = new ArrayList<>();
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rst = pst.executeQuery()) {
            while (rst.next())
                departaments.add(new Departament(
                        rst.getString("uuid"),
                        rst.getString("name"),
                        rst.getString("created_at"),
                        rst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return departaments;
    }

    @Override
    public boolean delete(Departament departament) {
        String sql = "DELETE FROM departament WHERE uuid = ?";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, departament.getId().toString());
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        return delete(new Departament(id));
    }

    @Override
    public boolean update(Departament departament) {
        String sql = "UPDATE departament SET name=?, updated_at=? WHERE uuid = ?";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, departament.getName());
            pst.setString(2, LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            pst.setString(3, departament.getId().toString());
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
