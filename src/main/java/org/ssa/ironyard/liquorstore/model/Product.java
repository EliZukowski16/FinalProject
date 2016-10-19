package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;

public class Product extends AbstractDomainObject implements DomainObject
{
    private final CoreProduct coreProduct;
    private final BaseUnit baseUnit; // e.g. 12oz can, 30 pack, etc.
    private final Integer quantity;
    private final Integer inventory;
    private final BigDecimal price;

    public enum BaseUnit
    {
        _12OZ_CAN("12 oz can"), _12OZ_BOTTLE("12 oz bottle"),_16OZ_CAN("16 oz can"),_16OZ_BOTTLE("16 oz bottle"),_500ML_CAN("500 mL can"),
        _500ML_BOTTLE("500 mL bottle"),_600ML_BOTTLE("600 mL bottle"),_1140ML_BOTTLE("40 oz bottle"),
        
        _750ML_BOTTLE("750 mL bottle"),_1000ML_BOTTLE("1 L bottle"),_1500ML_BOTTLE("1.5 L bottle"),_1750ML_BOTTLE("1.75 L bottle"),_2000ML_BOTTLE("2 L bottle"),
        _3000ML_BOTTLE("3 L bottle"),_4000ML_BOX("4000 mL Box"),
        
        _NA("na");

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

    @Override
    public Product clone()
    {
        return this.of().coreProduct(this.getCoreProduct()).build();
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
    
    public Builder of()
    {
        return new Builder(this);
    }

    public static class Builder
    {
        private Integer id;
        private Boolean loaded;
        private CoreProduct coreProduct;
        private BaseUnit baseUnit; // e.g. 12oz can, 30 pack, etc.
        private Integer quantity;
        private Integer inventory;
        private BigDecimal price;

        public Builder()
        {
        }

        public Builder(Product product)
        {
            this.loaded = product.isLoaded();
            this.id = product.getId();
            this.coreProduct = product.getCoreProduct();
            this.baseUnit = product.getBaseUnitType();
            this.quantity = product.getQuantity();
            this.inventory = product.getInventory();
            this.price = product.getPrice();
        }

        public Builder id(Integer id)
        {
            this.id = id;
            return this;
        }

        public Builder coreProduct(CoreProduct coreProduct)
        {
            this.coreProduct = coreProduct;
            return this;
        }

        public Builder baseUnit(BaseUnit baseUnit)
        {
            this.baseUnit = baseUnit;
            return this;
        }

        public Builder quantity(Integer quantity)
        {
            this.quantity = quantity;
            return this;
        }

        public Builder inventory(Integer inventory)
        {
            this.inventory = inventory;
            return this;
        }

        public Builder price(BigDecimal price)
        {
            this.price = price;
            return this;
        }
        
        public Builder price(Double price)
        {
            this.price = BigDecimal.valueOf(price);
            return this;
        }

        public Builder loaded(Boolean loaded)
        {
            this.loaded = loaded;
            return this;
        }

        public Product build()
        {
            return new Product(this.id, this.coreProduct, this.baseUnit, this.quantity, this.inventory, this.price, this.loaded);
        }
    }


}
