package org.ssa.ironyard.liquorstore.dao;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.Order;

public abstract class AbstractDAOOrder extends AbstractSpringDAO<Order> implements DAO<Order>
{

    protected AbstractDAOOrder(ORM<Order> orm, DataSource dataSource)
    {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
    }

}
