package gestion_boutique.core.repository.impl;

import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.repository.Repository;


public class RepositoryListImpl <T> implements Repository<T>{
      protected List <T> data= new ArrayList<>();

    @Override
    public boolean insert(T value){
        return data.add(value);
    }

    @Override
    public List<T> selectAll(){
        return data;
    }
}
