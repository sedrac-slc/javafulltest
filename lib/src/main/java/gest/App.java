package gest;

public class App {

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        // CrudDB<Person> crud = new PersonDB();

        long end = System.currentTimeMillis();
        System.out.printf("%s ms\n", (end - start));
    }

}
