package org.ssa.ironyard.liquorstore.dao;

import javax.sql.DataSource;

import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.model.Admin;

public abstract class AbstractDAOAdmin extends AbstractSpringDAO<Admin> implements DAOAdmin
{

    protected AbstractDAOAdmin(ORM<Admin> orm, DataSource dataSource)
    {
        super(orm, dataSource);
    }

}
