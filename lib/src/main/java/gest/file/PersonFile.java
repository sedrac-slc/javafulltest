package gest.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.util.List;

import gest.entity.Person;

public final class PersonFile extends StorageFile {

    public static final String FOLDER;
    public static final String FILE_LIST;
    public static final String FILE_REMOVE;

    static {
        FOLDER = FOLDER_STORAGE_DEFAULT + "/person";
        FILE_LIST = FOLDER + "/list.txt";
        FILE_REMOVE = FOLDER + "/remove.txt";
    }

    public static boolean createFiles() {
        try {
            Files.createFile(Paths.get(FILE_LIST));
            Files.createFile(Paths.get(FILE_REMOVE));
            return true;
        } catch (FileAlreadyExistsException e) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean createDirectory() {
        try {
            Files.createDirectories(Paths.get(FOLDER));
            return true;
        } catch (FileAlreadyExistsException e) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void add(Person person) {
        Path path = Paths.get(FILE_LIST);
        try (BufferedWriter bfw = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            if (Files.size(path) > 0)
                bfw.newLine();
            bfw.append(person.toString());
            bfw.flush();
        } catch (IOException e) {

        }
    }

    public static void imprimeBufferedReader() {
        Path path = Paths.get(FILE_LIST);
        try (BufferedReader bfr = Files.newBufferedReader(path)) {
            String row = "";
            while ((row = bfr.readLine()) != null)
                System.out.println(row);
        } catch (IOException e) {

        }
    }

    public static List<String> list() {
        try {
            Path path = Paths.get(FILE_LIST);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return List.of();
        }
    }

    public static void imprime() {
        Path path = Paths.get(FILE_LIST);
        try {
            Files.lines(path, StandardCharsets.UTF_8).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(Person person) {
        try {
            List<String> linhas = Files.readAllLines(Paths.get(FILE_LIST));
            return linhas.stream().allMatch(linha -> linha.contains(person.toString()));
        } catch (IOException e) {
            return false;
        }
    }

}