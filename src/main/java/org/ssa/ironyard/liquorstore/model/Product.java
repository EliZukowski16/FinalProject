package org.ssa.ironyard.liquorstore.model;

public class Product extends AbstractDomainObject implements DomainObject
{
    private final CoreProduct coreProductId;
    private final BaseUnit baseUnit; // e.g. 12oz can, 30 pack, etc.
    private final Integer quantity;
    private final Integer inventory;

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

    public Product(Integer id, CoreProduct coreProductId, BaseUnit baseUnit, Integer quantity, Integer inventory)
    {
        super(id);
        this.coreProductId = coreProductId;
        this.baseUnit = baseUnit;
        this.quantity = quantity;
        this.inventory = inventory;
    }

    public Product(CoreProduct coreProductId, BaseUnit baseUnit, Integer quantity, Integer inventory)
    {
        this(null, coreProductId, baseUnit, quantity, inventory);
    }

    public BaseUnit getBaseUnit()
    {
        return baseUnit;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getInventory()
    {
        return inventory;
    }

    public CoreProduct getCoreProductId()
    {
        return coreProductId;
    }

    @Override
    public Product clone()
    {
        Product copy;
        try
        {
            copy = (Product) super.clone();
            return copy;
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
            return null;
        }
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
        if (coreProductId != other.coreProductId)
            return false;
        if (this.getId() != other.getId())
            return false;
        if (inventory != other.inventory)
            return false;
        if (quantity != other.quantity)
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
