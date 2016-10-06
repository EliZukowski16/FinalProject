package org.ssa.ironyard.liquorstore.dao;

import org.ssa.ironyard.liquorstore.model.Product;

public interface DAOProduct extends DAO<Product>
{

    Product readEager(Integer id);

}
