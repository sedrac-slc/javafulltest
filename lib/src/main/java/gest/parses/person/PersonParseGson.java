package gest.parses.person;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import gest.config.DeserializerLocalDate;
import gest.config.DeserializerLocalDateTime;
import gest.entity.Person;
import gest.interfaces.ParseObj;

public class PersonParseGson implements ParseObj<Person> {

    @Override
    public Optional<Person> json(String jsonText) {
        try {
            // Gson gson = new Gson();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new DeserializerLocalDate())
                    .registerTypeAdapter(LocalDateTime.class, new DeserializerLocalDateTime())
                    .create();
            Person person = gson.fromJson(jsonText, Person.class);
            return Optional.of(person);
        } catch (JsonSyntaxException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Person> toString(String toStringText) {
        toStringText = toStringText.replace("Person", "")
                .replace("(", "{\"")
                .replace(")", "\"}")
                .replace("=", "\":\"")
                .replace(", ", "\",\"");
        return json(toStringText);
    }

}
