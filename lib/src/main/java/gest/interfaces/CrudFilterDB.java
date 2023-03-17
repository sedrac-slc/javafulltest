package gest.interfaces;

import java.util.List;

import javax.sql.rowset.Predicate;

public interface CrudFilterDB<T> extends CrudDB<T> {
    List<T> findAll(Predicate p);
}
