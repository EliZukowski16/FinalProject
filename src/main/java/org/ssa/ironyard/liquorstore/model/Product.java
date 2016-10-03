package org.ssa.ironyard.liquorstore.model;

public class Product implements DomainObject
{
    private final Integer id;
    private final Integer coreProductId;
    private String baseUnit; //e.g. 12oz can, 30 pack, etc.
    private int quantity;
    private int inventory;

    
    public Product(Integer id, Integer coreProductId, String baseUnit, int quantity, int inventory)
    {
        this.id = id;
        this.coreProductId = coreProductId;
        this.baseUnit = baseUnit;
        this.quantity = quantity;
        this.inventory = inventory;        
    }
    
    
    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCoreProductId() {
        return coreProductId;
    }
    
    @Override
    public Product clone()
    {
        Product copy;
        try
        {
            copy = (Product) super.clone();
            copy.setBaseUnit(this.getBaseUnit());
            copy.setQuantity(this.getQuantity());
            copy.setInventory(this.getInventory());
            return copy;
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baseUnit == null) ? 0 : baseUnit.hashCode());
        result = prime * result + coreProductId;
        result = prime * result + id;
        result = prime * result + inventory;
        result = prime * result + quantity;
        return result;
    }
    
    @Override
    public boolean deeplyEquals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (baseUnit == null) {
            if (other.baseUnit != null)
                return false;
        } else if (!baseUnit.equals(other.baseUnit))
            return false;
        if (coreProductId != other.coreProductId)
            return false;
        if (id != other.id)
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
        if(this.getId() == null)
        {
            if(other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }
    
    

}
