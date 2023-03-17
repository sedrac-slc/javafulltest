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
import gest.entity.PersonDepartament;

public class PersonDepartamentDB implements CrudDB<PersonDepartament> {

    @Override
    public boolean add(PersonDepartament personDepartament) {
        String sql = "INSERT INTO person_departament(person_uuid,departament_uuid) VALUES (?,?)";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, personDepartament.getPerson().getId().toString());
            pst.setString(2, personDepartament.getDepartament().getId().toString());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<PersonDepartament> find(UUID id) {
        String sql = "SELECT * FROM person_departament WHERE uuid=? LIMIT 1";
        PersonDepartament personDepartament = null;
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id.toString());
            ResultSet rst = pst.executeQuery();
            while (rst.next())
                personDepartament = new PersonDepartament(
                        rst.getString("uuid"),
                        rst.getString("person_uuid"),
                        rst.getString("departament_uuid"),
                        rst.getString("created_at"),
                        rst.getString("updated_at"));
            rst.close();
            return Optional.ofNullable(personDepartament);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonDepartament> findAll() {
        String sql = "SELECT * FROM person_departament";
        List<PersonDepartament> personDepartaments = new ArrayList<>();
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rst = pst.executeQuery()) {
            while (rst.next())
                personDepartaments.add(new PersonDepartament(
                        rst.getString("uuid"),
                        rst.getString("person_uuid"),
                        rst.getString("departament_uuid"),
                        rst.getString("created_at"),
                        rst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return personDepartaments;
    }

    @Override
    public boolean delete(PersonDepartament personDepartament) {
        String sql = "DELETE FROM person_departament WHERE uuid = ?";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, personDepartament.getId().toString());
            return pst.executeUpdate() == 1;
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
        String sql = "UPDATE person_departament SET person_uuid=?, departament_id=?, updated_at=? WHERE uuid = ?";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, personDepartament.getPerson().getId().toString());
            pst.setString(2, personDepartament.getDepartament().getId().toString());
            pst.setString(3, LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            pst.setString(4, personDepartament.getId().toString());
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

}
