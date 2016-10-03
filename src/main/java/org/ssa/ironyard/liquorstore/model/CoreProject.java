package org.ssa.ironyard.liquorstore.model;

import java.util.List;

public class CoreProject implements DomainObject
{
    private final Integer id;
    private final String name;
    private final List<String> tags;
    private final Type type;
    private final String subType;
    private final String description;
    
    
    public CoreProject(int id, String name, List<String> tags, Type type, String subType, String description)
    {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.type = type;
        this.subType = subType;
        this.description = description;
    }


    public CoreProject(String name, List<String> tags, Type type, String subType, String description)
    {
        this.id = null;
        this.name = name;
        this.tags = tags;
        this.type = type;
        this.subType = subType;
        this.description = description;
    }



    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public enum Type
    {
        
        BEER,WINE,SPIRTS;
        
        private String type;
        
        public String getType()
        {
            return type;
        }
        
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
        CoreProject other = (CoreProject) obj;
        if (id != other.id)
            return false;
        return true;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }


    public boolean deeplyEquals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CoreProject other = (CoreProject) obj;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (id != other.id)
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
    
        CoreProject copy;
        
        try
        {
            copy = (CoreProject) super.clone();
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
        return "CoreProject [id=" + id + ", name=" + name + ", tags=" + tags + ", subType=" + subType + ", description=" + description + "]";
    }
    
    
    
    
    
    
    
    
}
