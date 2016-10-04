package org.ssa.ironyard.liquorstore.dao;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.Customer;

public abstract class AbstractDAOCustomer extends AbstractSpringDAO<Customer> implements DAOCustomer
{

    protected AbstractDAOCustomer(ORM<Customer> orm, DataSource dataSource)
    {
        super(orm, dataSource);
        // TODO Auto-generated constructor stub
    }
}
