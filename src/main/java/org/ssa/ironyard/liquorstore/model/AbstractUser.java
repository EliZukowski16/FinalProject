package org.ssa.ironyard.liquorstore.model;

public abstract class AbstractUser extends AbstractDomainObject implements User
{
    
    public Boolean isAdmin;

    public AbstractUser(Integer id)
    {
        super(id);
    }

    @Override
    public Boolean isAdmin()
    {
        return this.isAdmin;
    }

}
