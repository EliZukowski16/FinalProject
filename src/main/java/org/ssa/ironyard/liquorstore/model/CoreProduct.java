package org.ssa.ironyard.liquorstore.model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CoreProduct extends AbstractDomainObject implements DomainObject
{
    private final String name;
    private final List<Tag> tags;
    private final Type type;
    private final String subType;
    private final String description;
    private final String fullSizeImage;
    private final String thumbnail;

    public enum Type
    {
        BEER("beer"), WINE("wine"), SPIRITS("spirits"),CIDERS("ciders");

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
            alcoholType = alcoholType.toLowerCase();

            for (Type t : values())
            {
                if (t.alcoholType.equals(alcoholType))
                    return t;
            }
            return null;
        }
    }

    public CoreProduct(Integer id, String name, List<Tag> tags, Type type, String subType, String description)
    {
        this(id, name, tags, type, subType, description,null, null, false);
    }

    public CoreProduct(String name, List<Tag> tags, Type type, String subType, String description)
    {
        this(null, name, tags, type, subType, description, null, null, false);
    }

    public CoreProduct(Integer id, String name, List<Tag> tags, Type type, String subType, String description, String fullSizeImage,
            String thumbnail, boolean loaded)
    {
        super(id, loaded);
        this.name = name;
        this.tags = (tags == null ? new ArrayList<>() : tags);
        this.type = type;
        this.subType = subType;
        this.description = description;
        this.fullSizeImage = fullSizeImage;
        this.thumbnail = thumbnail;
    }

    public String getName()
    {
        return name;
    }

    public List<Tag> getTags()
    {
        return new ArrayList<>(this.tags);
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
    
    public String getFullSizeImage()
    {
        return fullSizeImage;
    }
    
    public String getThumbnail()
    {
        return thumbnail;
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

        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
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

    public CoreProduct clone()
    {
        return this.of().tags(this.tags).build();
    }

    @Override
    public String toString()
    {
        return "CoreProject [id=" + this.getId() + ", name=" + name + ", tags=" + tags + ", subType=" + subType
                + ", description=" + description + "]";
    }

    public static class Tag
    {
        protected final String name;

        public Tag(String name)
        {
            this.name = deAccent(name.toLowerCase());
        }

        public String getName()
        {
            return name;
        }
        
        public String deAccent(String str) {
            String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(nfdNormalizedString).replaceAll("");
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
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
            Tag other = (Tag) obj;
            if (name == null)
            {
                if (other.name != null)
                    return false;
            }
            else if (!name.equals(other.name))
                return false;
            return true;
        }

    }
    
    public Builder of()
    {
        return new Builder(this);
    }

    public static class Builder
    {
        private Boolean loaded;
        private Integer id;
        private String name;
        private List<Tag> tags = new ArrayList<>();
        private Type type;
        private String subType;
        private String description;
        private String fullSizeImage;
        private String thumbnail;

        public Builder()
        {
        }

        public Builder(CoreProduct coreProduct)
        {
            this.loaded = coreProduct.isLoaded();
            this.id = coreProduct.getId();
            this.name = coreProduct.name;
            this.tags = coreProduct.getTags();
            this.type = coreProduct.getType(); // because address is mutable
            this.subType = coreProduct.getSubType();
            this.description = coreProduct.description;
            this.fullSizeImage = coreProduct.getFullSizeImage();
            this.thumbnail = coreProduct.getThumbnail();
        }

        public Builder id(Integer id)
        {
            this.id = id;
            return this;
        }

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder tags(List<Tag> tags)
        {
            this.tags = tags;
            return this;
        }
        
        public Builder addTag(Tag tag)
        {
            this.tags.add(tag);
            return this;
        }
        
        public Builder addTag(String tag)
        {
            this.tags.add(new Tag(tag));
            return this;
        }

        public Builder type(Type type)
        {
            this.type = type;
            return this;
        }

        public Builder subType(String subType)
        {
            this.subType = subType;
            return this;
        }

        public Builder description(String description)
        {
            this.description = description;
            return this;
        }

        public Builder loaded(Boolean loaded)
        {
            this.loaded = loaded;
            return this;
        }
        
        public Builder fullSizeImage(String fullSizeImage)
        {
            this.fullSizeImage = fullSizeImage;
            return this;
        }
        
        public Builder thumbnail(String thumbnail)
        {
            this.thumbnail = thumbnail;
            return this;
        }

        public CoreProduct build()
        {
            return new CoreProduct(this.id, this.name, this.tags, this.type, this.subType, this.description, this.fullSizeImage, this.thumbnail,
                    this.loaded);
        }
    }

}
