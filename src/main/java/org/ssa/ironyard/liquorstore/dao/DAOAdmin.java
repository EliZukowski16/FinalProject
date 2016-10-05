package org.ssa.ironyard.liquorstore.dao;

import org.ssa.ironyard.liquorstore.model.Admin;

public interface DAOAdmin extends DAO<Admin>
{

    public Admin readByUserName(String username);

}
