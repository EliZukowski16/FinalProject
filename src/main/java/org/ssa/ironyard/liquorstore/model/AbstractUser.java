package org.ssa.ironyard.liquorstore.model;

public abstract class AbstractUser extends AbstractDomainObject implements User
{

    public AbstractUser(Integer id)
    {
        this(id, false);
    }

    public AbstractUser(Integer id, Boolean loaded)
    {
        super(id, loaded);
    }

}
