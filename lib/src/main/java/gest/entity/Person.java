package gest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import gest.enums.Gender;
import gest.util.ConstantDateFormat;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {

    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Person() {
    }

    public Person(UUID id) {
        this.id = id;
    }

    public Person(String id, String firstName, String lastName, String birthday, String gender, String createdAt,
            String updatedAt) {
        this.id = UUID.fromString(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = LocalDate.parse(birthday);
        this.gender = Gender.valueOf(gender);
        this.createdAt = LocalDateTime.parse(createdAt, ConstantDateFormat.FORMAT_LOCALCURRENT_LOCAL);
        this.updatedAt = LocalDateTime.parse(updatedAt, ConstantDateFormat.FORMAT_LOCALCURRENT_LOCAL);
    }

}
