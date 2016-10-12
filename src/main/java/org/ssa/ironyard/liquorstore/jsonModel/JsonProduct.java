package org.ssa.ironyard.liquorstore.jsonModel;

import java.math.BigDecimal;

public class JsonProduct
{
    int productID;
    int qty;
    BigDecimal price;
    
    public JsonProduct(int productID, int qty, BigDecimal price)
    {
        this.productID = productID;
        this.qty = qty;
        this.price = price;
    }
    public JsonProduct(){}
    public int getProductID()
    {
        return productID;
    }
    public void setProductID(int productID)
    {
        this.productID = productID;
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
