package org.ssa.ironyard.liquorstore.model;

public abstract class AbstractDomainObject
{
    private final Integer id;
    private Boolean loaded;

    private AbstractDomainObject(Integer id, Boolean loaded)
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

    public void setLoaded(Boolean loaded)
    {
        this.loaded = loaded;
    }
    
}
