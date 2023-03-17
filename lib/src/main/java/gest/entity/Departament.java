package gest.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import gest.util.ConstantDateFormat;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Departament {

    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Departament() {
    }

    public Departament(UUID id) {
        this.id = id;
    }

    public Departament(String name) {
        this.name = name;
    }

    public Departament(String id, String name, String createdAt, String updatedAt) {
        this.id = UUID.fromString(id);
        this.name = name;
        this.createdAt = LocalDateTime.parse(createdAt, ConstantDateFormat.FORMAT_LOCALCURRENT_LOCAL);
        this.updatedAt = LocalDateTime.parse(updatedAt, ConstantDateFormat.FORMAT_LOCALCURRENT_LOCAL);
    }

}
