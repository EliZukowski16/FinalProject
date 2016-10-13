package org.ssa.ironyard.liquorstore.jsonModel;

import java.math.BigDecimal;

public class JsonOrder
{
    BigDecimal total;
    JsonProduct products;
    String orderYear;
    String orderMonth;
    String orderDay;
    public JsonOrder(BigDecimal total, JsonProduct products, String orderYear, String orderMonth, String orderDay)
    {
        super();
        this.total = total;
        this.products = products;
        this.orderYear = orderYear;
        this.orderMonth = orderMonth;
        this.orderDay = orderDay;
    }
    public JsonOrder()
    {
        super();
    }
    public BigDecimal getTotal()
    {
        return total;
    }
    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }
    public JsonProduct getProducts()
    {
        return products;
    }
    public void setProducts(JsonProduct products)
    {
        this.products = products;
    }
    public String getOrderYear()
    {
        return orderYear;
    }
    public void setOrderYear(String orderYear)
    {
        this.orderYear = orderYear;
    }
    public String getOrderMonth()
    {
        return orderMonth;
    }
    public void setOrderMonth(String orderMonth)
    {
        this.orderMonth = orderMonth;
    }
    public String getOrderDay()
    {
        return orderDay;
    }
    public void setOrderDay(String orderDay)
    {
        this.orderDay = orderDay;
    }
    
    
    
    
    
}
