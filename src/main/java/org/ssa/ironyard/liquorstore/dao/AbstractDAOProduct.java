package org.ssa.ironyard.liquorstore.dao;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.Product;

public abstract class AbstractDAOProduct extends AbstractSpringDAO<Product> implements DAOProduct
{

    protected AbstractDAOProduct(ORM<Product> orm, DataSource dataSource)
    {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
    }


}
