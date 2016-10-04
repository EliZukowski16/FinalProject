package org.ssa.ironyard.liquorstore.dao;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.CoreProduct;

public abstract class AbstractDAOCoreProduct extends AbstractSpringDAO<CoreProduct> implements DAO<CoreProduct>
{

    protected AbstractDAOCoreProduct(ORM<CoreProduct> orm, DataSource dataSource)
    {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
    }

}
