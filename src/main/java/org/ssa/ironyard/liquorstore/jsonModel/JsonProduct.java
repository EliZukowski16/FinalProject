package org.ssa.ironyard.liquorstore.jsonModel;

import java.math.BigDecimal;

public class JsonProduct
{
    int id;
    int qty;
    BigDecimal price;
    
    public JsonProduct(int id, int qty, BigDecimal price)
    {
        this.id = id;
        this.qty = qty;
        this.price = price;
    }
    public JsonProduct(){}
    public int getid()
    {
        return id;
    }
    public void setid(int id)
    {
        this.id = id;
    }
    public int getQty()
    {
        return qty;
    }
    public void setQty(int qty)
    {
        this.qty = qty;
    }
    public BigDecimal getPrice()
    {
        return price;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
    
    
}
