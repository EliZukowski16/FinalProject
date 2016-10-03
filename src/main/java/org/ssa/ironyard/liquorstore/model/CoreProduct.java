package org.ssa.ironyard.liquorstore.model;

import java.util.List;

public class CoreProduct extends AbstractDomainObject implements DomainObject
{
    private final String name;
    private final List<String> tags;
    private final Type type;
    private final String subType;
    private final String description;

    public enum Type
    {
        BEER("beer"), WINE("wine"), SPIRITS("spirits");

        private String alcoholType;

        private Type(String alcoholType)
        {
            this.alcoholType = alcoholType;
        }

        @Override
        public String toString()
        {
            return this.alcoholType;
        }

        public static Type getInstance(String alcoholType)
        {
            for (Type t : values())
            {
                if (t.alcoholType.equals(alcoholType))
                    return t;
            }
            return null;
        }
    }

    public CoreProduct(Integer id, String name, List<String> tags, Type type, String subType, String description)
    {
        super(id);
        this.name = name;
        this.tags = tags;
        this.type = type;
        this.subType = subType;
        this.description = description;
    }

    public CoreProduct(String name, List<String> tags, Type type, String subType, String description)
    {
        this(null, name, tags, type, subType, description);
    }

    public String getName()
    {
        return name;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public Type getType()
    {
        return type;
    }

    public String getSubType()
    {
        return subType;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CoreProduct other = (CoreProduct) obj;
        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getId();
        return result;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CoreProduct other = (CoreProduct) obj;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (this.getId() != other.getId())
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (subType == null)
        {
            if (other.subType != null)
                return false;
        }
        else if (!subType.equals(other.subType))
            return false;
        if (tags == null)
        {
            if (other.tags != null)
                return false;
        }
        else if (!tags.equals(other.tags))
            return false;
        return true;
    }

    public DomainObject clone()
    {

        CoreProduct copy;

        try
        {
            copy = (CoreProduct) super.clone();
            return copy;
        }
        catch (CloneNotSupportedException e)
        {
            return null;
        }

    }

    @Override
    public String toString()
    {
        return "CoreProject [id=" + this.getId() + ", name=" + name + ", tags=" + tags + ", subType=" + subType
                + ", description=" + description + "]";
    }

}
