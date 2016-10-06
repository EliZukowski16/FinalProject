package org.ssa.ironyard.liquorstore.dao;

public interface DAO<T>
{

    T read(Integer id);

    T insert(T domain);

    T update(T domain);
    
    

    boolean delete(Integer id);

    void clear();

}
