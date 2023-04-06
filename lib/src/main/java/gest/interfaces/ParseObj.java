package gest.interfaces;

import java.util.Optional;

public interface ParseObj<T> {
    Optional<T> json(String text);

    Optional<T> toString(String text);
}
