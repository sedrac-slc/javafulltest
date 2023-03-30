package gest;

import java.text.ParseException;

import gest.entity.Person;

public class App {

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        // CrudDB<Person> crud = new PersonDB();
        try {
            String toString = "Person(id=ec9f1009-c33d-11ed-b159-bcaec504bfe2, firstName=Laura, lastName=Luena, birthday=1999-10-10, gender=FEMALE, createdAt=2023-03-15T10:10:05, updatedAt=2023-03-16T09:54:57)";
            System.out.println(Person.fromString(toString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.printf("%s ms\n", (end - start));
    }

}
