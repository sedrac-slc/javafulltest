package gest.file.storagejson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import gest.entity.Person;

public class PersonFileJson {
    public static void main(String[] args) throws IOException {
        String url = System.getProperty("user.dir") + "/storage/json/person/list.json";
        Path path = Paths.get(url);

        // Ler o conteúdo do arquivo JSON para uma lista de objetos Java
        List<String> linhas = Files.readAllLines(path, StandardCharsets.UTF_8);

        // Converter o conteúdo do arquivo para uma lista de objetos Java
        List<Object> objetos = new Gson().fromJson(linhas.get(0), new TypeToken<List<Object>>() {
        }.getType());
        objetos.forEach(System.out::println);
        // Adicionar o objeto que deseja inserir no meio da lista
        // Person objetoNovo = new Person(); // Substitua "Object" pelo nome da classe
        // do seu objeto
        // objetos.add(2, objetoNovo); // Insere o objeto na terceira posição (índice 2)

        // Escrever a lista atualizada de volta no arquivo JSON
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // String jsonAtualizado = gson.toJson(objetos);

        // Files.write(path, jsonAtualizado.getBytes(StandardCharsets.UTF_8));
    }

}
