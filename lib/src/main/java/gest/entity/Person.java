package gest.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import gest.enums.Gender;
import gest.util.ConstantDateFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 3905542041950251007L;
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public static Person fromString(String text) throws ParseException {
        String rgx = "Person\\(id=.+, firstName=.+, lastName=.+, birthday=.+, gender=(FEMALE|MALE), createdAt=.+, updatedAt=.+\\)";
        if (!text.matches(rgx))
            throw new ParseException("Not convert", 0);
        System.out.println();
        return null;
    }

    public String toJson() {
        return "{"
                + "\"id\": \"" + id + "\","
                + "\"firstName\": \"" + firstName + "\","
                + "\"lastName\": \"" + lastName + "\","
                + "\"birthday\": \"" + birthday + "\","
                + "\"gender\": \"" + gender + "\","
                + "\"createdAt\": \"" + createdAt + "\","
                + "\"updatedAt\": \"" + updatedAt + "\""
                + "}";
    }

}
