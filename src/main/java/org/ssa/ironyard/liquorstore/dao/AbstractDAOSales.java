package org.ssa.ironyard.liquorstore.dao;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.Sales;

public abstract class AbstractDAOSales extends AbstractSpringDAO<Sales> implements DAO<Sales>
{

    protected AbstractDAOSales(ORM<Sales> orm, DataSource dataSource)
    {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
    }

}
