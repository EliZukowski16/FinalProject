package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;

public class Product extends AbstractDomainObject implements DomainObject
{
    private CoreProduct coreProduct;
    private final BaseUnit baseUnit; // e.g. 12oz can, 30 pack, etc.
    private final Integer quantity;
    private final Integer inventory;
    private final BigDecimal price;

    public enum BaseUnit
    {
        _12OZ_CAN("12 oz can"), _12OZ_BOTTLE("12 oz bottle"), _750ML_BOTTLE("750 ml bottle");

        private String unit;

        private BaseUnit(String unit)
        {
            this.unit = unit;
        }

        @Override
        public String toString()
        {
            return this.unit;
        }

        public static BaseUnit getInstance(String unit)
        {
            for (BaseUnit t : values())
            {
                if (t.unit.equals(unit))
                    return t;
            }
            return null;
        }
    }

    public Product(Integer id, CoreProduct coreProduct, BaseUnit baseUnit, Integer quantity, Integer inventory, BigDecimal price)
    {
        this(id, coreProduct, baseUnit, quantity, inventory, price, false);
    }

    public Product(CoreProduct coreProduct, BaseUnit baseUnit, Integer quantity, Integer inventory, BigDecimal price)
    {
        this(null, coreProduct, baseUnit, quantity, inventory, price);
    }
    
    public Product(Integer id, CoreProduct coreProduct, BaseUnit baseUnit, Integer quantity, Integer inventory,
            BigDecimal price, Boolean loaded)
    {
        super(id, false);
        this.coreProduct = coreProduct;
        this.baseUnit = baseUnit;
        this.quantity = quantity;
        this.inventory = inventory;
        this.price = price;
    }

    public BaseUnit getBaseUnitType()
    {
        return baseUnit;
    }

    public String getBaseUnit()
    {
        if(this.baseUnit == null)
            return null;
        
        return baseUnit.toString();
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public Integer getInventory()
    {
        return inventory;
    }
    
    public BigDecimal getPrice()
    {
        return price;
    }

    public CoreProduct getCoreProduct()
    {
        return coreProduct;
    }
    
    public void setCoreProduct(CoreProduct coreProduct)
    {
        this.coreProduct = coreProduct;
    }

    @Override
    public Product clone()
    {
        Product copy;
        copy = (Product) super.clone();
        copy.setCoreProduct(this.coreProduct);
        return copy;
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
        Product other = (Product) obj;
        if (baseUnit == null)
        {
            if (other.baseUnit != null)
                return false;
        }
        else if (!baseUnit.equals(other.baseUnit))
            return false;
        
        if(coreProduct == null)
        {
            if(other.coreProduct != null)
                return false;
        }
        else if (!coreProduct.equals(other.coreProduct))
            return false;
        
        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        
        if(inventory == null)
        {
            if(other.inventory != null)
                return false;
        }
        else if (inventory != other.inventory)
            return false;
        
        if(quantity == null)
        {
            if(other.quantity != null)
                return false;
        }
        else if (quantity != other.quantity)
            return false;
        
        if(price == null)
        {
            if(other.price != null)
                return false;
        }     
        else if(this.price.compareTo(other.price) != 0)
            return false;
        return true;
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
        Product other = (Product) obj;
        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }

}
