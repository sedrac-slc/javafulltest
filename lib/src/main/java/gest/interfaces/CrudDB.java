package gest.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudDB<T> {

    boolean add(T t);

    Optional<T> find(UUID id);

    List<T> findAll();

    boolean delete(T t);

    boolean delete(UUID id);

    boolean update(T t);

    default Optional<T> find(String id) {
        try {
            return find(UUID.fromString(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    default boolean delete(String id) {
        try {
            return delete(UUID.fromString(id));
        } catch (Exception e) {
            return false;
        }
    }

}
