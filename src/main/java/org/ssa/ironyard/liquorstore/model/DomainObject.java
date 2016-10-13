package org.ssa.ironyard.liquorstore.model;

import java.util.Objects;

/**
 *
 * @author thurston
 */
public interface DomainObject extends Cloneable
{
    Integer getId();

    default Boolean isLoaded()
    {
        return false;
    }

    default boolean deeplyEquals(DomainObject other)
    {
        if (!Objects.deepEquals(this, other))
            return false;
        return true;
    }

    /**
     * Re-declare for emphasis
     */

    @Override
    String toString();

    @Override
    boolean equals(Object other);

    @Override
    int hashCode();

    public DomainObject clone();
}
