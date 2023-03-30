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
import gest.entity.Person;
import gest.file.PersonFile;

public class PersonDB implements CrudDB<Person> {

    @Override
    public boolean add(Person person) {
        String sql = "INSERT INTO person VALUES (DEFAULT,?,?,?,?,?,DEFAULT)";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, person.getFirstName());
            pst.setString(2, person.getLastName());
            pst.setString(3, person.getBirthday().toString());
            pst.setString(4, person.getGender().name());
            pst.setString(5, LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            pst.executeUpdate();
            PersonFile.add(person);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<Person> find(UUID id) {
        String sql = "SELECT * FROM person WHERE uuid=? LIMIT 1";
        Person person = new Person();
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, id.toString());
            ResultSet rst = pst.executeQuery();
            while (rst.next())
                person = new Person(
                        rst.getString("uuid"),
                        rst.getString("first_name"),
                        rst.getString("last_name"),
                        rst.getString("birthday"),
                        rst.getString("gender"),
                        rst.getString("created_at"),
                        rst.getString("updated_at"));
            rst.close();
            return person.getId() == null ? Optional.empty() : Optional.of(person);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        List<Person> persons = new ArrayList<>();
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rst = pst.executeQuery()) {
            while (rst.next())
                persons.add(new Person(
                        rst.getString("uuid"),
                        rst.getString("first_name"),
                        rst.getString("last_name"),
                        rst.getString("birthday"),
                        rst.getString("gender"),
                        rst.getString("created_at"),
                        rst.getString("updated_at")));
        } catch (SQLException e) {
            return List.of();
        }
        return persons;
    }

    @Override
    public boolean delete(Person person) {
        String sql = "DELETE FROM person WHERE uuid = ?";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, person.getId().toString());
            return pst.executeUpdate() == 1;
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
        String sql = "UPDATE person SET first_name=?, last_name=?, birthday=?, gender=?, updated_at=? WHERE uuid = ?";
        try (Connection conn = DB.open();
                PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, person.getFirstName());
            pst.setString(2, person.getLastName());
            pst.setString(3, person.getBirthday().toString());
            pst.setString(4, person.getGender().name());
            pst.setString(5, LocalDateTime.now().format(ConstantDateFormat.FORMAT_LOCALCURRENT_MYSQL));
            pst.setString(6, person.getId().toString());
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

}
