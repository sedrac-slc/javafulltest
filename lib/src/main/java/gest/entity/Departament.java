package gest.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import gest.util.ConstantDateFormat;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Departament implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
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
