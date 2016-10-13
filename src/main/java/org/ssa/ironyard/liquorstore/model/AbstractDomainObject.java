package org.ssa.ironyard.liquorstore.model;

public abstract class AbstractDomainObject implements DomainObject
{
    private final Integer id;
    private final Boolean loaded;

    protected AbstractDomainObject(Integer id, Boolean loaded)
    {
        this.id = id;
        this.loaded = loaded;
    }

    protected AbstractDomainObject(Integer id)
    {
        this(id, false);
    }

    protected AbstractDomainObject()
    {
        this(null, false);
    }

    public Integer getId()
    {
        return id;
    }

    public boolean isLoaded()
    {
        return loaded;
    }
    
    public AbstractDomainObject clone()
    {
        try
        {
            AbstractDomainObject copy = (AbstractDomainObject) super.clone();
            return copy;
        }
        catch (CloneNotSupportedException e)
        {
            
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }
    
}
