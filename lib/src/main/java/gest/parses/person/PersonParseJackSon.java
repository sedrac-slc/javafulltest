package gest.parses.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.util.Optional;

import gest.entity.Person;
import gest.interfaces.ParseObj;
import gest.util.ConstantDateFormat;

public class PersonParseJackSon implements ParseObj<Person> {

    @Override
    public Optional<Person> json(String jsonText) {
        try {
            DateTimeFormatter formatter = ConstantDateFormat.FORMAT_PARSE_OBJECT;
            JavaTimeModule javaTimeModule = new JavaTimeModule();

            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));

            ObjectMapper objectMapper = new ObjectMapper().registerModule(javaTimeModule);
            Person person = objectMapper.readValue(jsonText, Person.class);

            return Optional.of(person);
        } catch (JsonProcessingException e) {
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
