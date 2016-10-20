package org.ssa.ironyard.liquorstore.dao;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.SalesDaily;

public interface DAO<T>
{

    T read(Integer id);

    T insert(T domain);

    T update(T domain);
    
    List<T> readAll();

    boolean delete(Integer id);

    void clear();

    List<T> readByIds(List<Integer> ids);

}
