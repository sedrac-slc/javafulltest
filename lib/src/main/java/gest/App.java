package gest;

import java.util.UUID;

import gest.entity.Person;
import gest.file.PersonFile;
import gest.interfaces.CrudDB;
import gest.persistence.PersonDB;

public class App {

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        CrudDB<Person> crud = new PersonDB();
        Person person = crud.find(UUID.fromString("ec9f1009-c33d-11ed-b159-bcaec504bfe2")).get();
        PersonFile.addList(person);
        long end = System.currentTimeMillis();
        System.out.printf("%s ms\n", (end - start));
    }

}
