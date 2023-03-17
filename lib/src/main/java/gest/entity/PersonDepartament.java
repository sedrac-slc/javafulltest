package gest.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import gest.interfaces.CrudDB;
import gest.persistence.DepartamentDB;
import gest.persistence.PersonDB;
import gest.util.ConstantDateFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class PersonDepartament {

    private UUID id;
    private Person person;
    private Departament departament;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private CrudDB<Person> personDB = new PersonDB();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private CrudDB<Departament> departamentDB = new DepartamentDB();

    public PersonDepartament(UUID id) {
        this.id = id;
    }

    public PersonDepartament(String id, String person, String departament, String createdAt, String updatedAt) {
        this.id = UUID.fromString(id);
        personDB.find(person).ifPresent(p -> this.person = p);
        departamentDB.find(departament).ifPresent(d -> this.departament = d);
        this.createdAt = LocalDateTime.parse(createdAt, ConstantDateFormat.FORMAT_LOCALCURRENT_LOCAL);
        this.updatedAt = LocalDateTime.parse(updatedAt, ConstantDateFormat.FORMAT_LOCALCURRENT_LOCAL);
    }

}
