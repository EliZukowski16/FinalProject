package org.ssa.ironyard.liquorstore.jsonModel;

import java.math.BigDecimal;

public class JsonBeer
{
    String name;
    String tags;
    int regular_price_in_cents;
    String primary_category;
    String secondary_category;
    String orgin;
    String package_unit_type;
    String package_unit_volume_in_milliliters;
    int total_package_units;
    int alcohol_content;
    String producer_name;
    String description;
    String image_thumb_url;
    String image_url;
    String tertiary_category;
    public JsonBeer(String name, String tags, int regular_price_in_cents, String primary_category, String secondary_category, String orgin, String package_unit_volume_in_milliliters,
            String package_unit_type, int total_package_units, int alcohol_content, String producer_name, String description, String image_thumb_url, String image_url, String tertiary_category)
    {
        super();
        this.name = name;
        this.tags = tags;
        this.regular_price_in_cents = regular_price_in_cents;
        this.primary_category = primary_category;
        this.secondary_category = secondary_category;
        this.orgin = orgin;
        this.package_unit_volume_in_milliliters = package_unit_volume_in_milliliters;
        this.package_unit_type = package_unit_type;
        this.total_package_units = total_package_units;
        this.alcohol_content = alcohol_content;
        this.producer_name = producer_name;
        this.description = description;
        this.image_thumb_url = image_thumb_url;
        this.image_url = image_url;
        this.tertiary_category = tertiary_category;
    }
    public JsonBeer()
    {
        super();
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getTags()
    {
        return tags;
    }
    public void setTags(String tags)
    {
        this.tags = tags;
    }
    public int getRegular_price_in_cents()
    {
        return regular_price_in_cents;
    }
    public void setRegular_price_in_cents(int regular_price_in_cents)
    {
        this.regular_price_in_cents = regular_price_in_cents;
    }
    public String getPrimary_category()
    {
        return primary_category;
    }
    public void setPrimary_category(String primary_category)
    {
        this.primary_category = primary_category;
    }
    public String getSecondary_category()
    {
        return secondary_category;
    }
    public void setSecondary_category(String secondary_category)
    {
        this.secondary_category = secondary_category;
    }
    public String getOrgin()
    {
        return orgin;
    }
    public void setOrgin(String orgin)
    {
        this.orgin = orgin;
    }
    public String getPackage_unit_volume_in_milliliters()
    {
        return package_unit_volume_in_milliliters;
    }
    public void setPackage_unit_volume_in_milliliters(String package_unit_volume_in_milliliters)
    {
        this.package_unit_volume_in_milliliters = package_unit_volume_in_milliliters;
    }
    public String getPackage_unit_type()
    {
        return package_unit_type;
    }
    public void setPackage_unit_type(String package_unit_type)
    {
        this.package_unit_type = package_unit_type;
    }
    public int getTotal_package_units()
    {
        return total_package_units;
    }
    public void setTotal_package_units(int total_package_units)
    {
        this.total_package_units = total_package_units;
    }
    public int getAlcohol_content()
    {
        return alcohol_content;
    }
    public void setAlcohol_content(int alcohol_content)
    {
        this.alcohol_content = alcohol_content;
    }
    public String getProducer_name()
    {
        return producer_name;
    }
    public void setProducer_name(String producer_name)
    {
        this.producer_name = producer_name;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getImage_thumb_url()
    {
        return image_thumb_url;
    }
    public void setImage_thumb_url(String image_thumb_url)
    {
        this.image_thumb_url = image_thumb_url;
    }
    public String getImage_url()
    {
        return image_url;
    }
    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }
    public String getTertiary_category()
    {
        return tertiary_category;
    }
    public void setTertiary_category(String tertiary_category)
    {
        this.tertiary_category = tertiary_category;
    }
    
    
}
