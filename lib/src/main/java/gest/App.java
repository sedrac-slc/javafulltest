package gest;

import gest.persistence.PersonDepartamentDB;

public class App {

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        var crud = new PersonDepartamentDB();
        crud.findAll().forEach(System.out::println);
        long end = System.currentTimeMillis();
        System.out.printf("%s ms\n", (end - start));
    }

}
