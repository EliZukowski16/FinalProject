package org.ssa.ironyard.liquorstore.model;

import java.util.Objects;

public interface DomainObject extends Cloneable
{
    Integer getId();
    
    default boolean isLoaded()
    {
        return false;
    }

    default boolean deeplyEquals(DomainObject other)
    {
        if (! Objects.deepEquals(this, other))
            return false;
        return true;
    }

    @Override
    String toString();

    @Override
    boolean equals(Object other);
    
    boolean deeplyEquals(Object obj);

    @Override
    int hashCode();


    DomainObject clone();
}
