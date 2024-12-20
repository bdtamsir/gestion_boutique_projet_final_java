package gestion_boutique.core.service;

import java.util.List;

public interface Service <T>{
    boolean create(T object);
    List<T> getAll();
}
