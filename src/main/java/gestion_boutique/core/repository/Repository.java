package gestion_boutique.core.repository;

import java.util.List;

public interface Repository <T> {
    boolean insert (T object);
    List<T> selectAll();
}
