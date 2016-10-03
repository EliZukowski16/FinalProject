package org.ssa.ironyard.liquorstore.model;

import java.util.Date;
import java.util.List;

public class Order implements DomainObject
{

    private final Integer id;
    private final int customerID;
    private final Date date;
    private final float total;
    private List<orderDetail> oD;

    public Order(int id, int customerID, Date date, float total, List<orderDetail> oD)
    {
        this.id = id;
        this.customerID = customerID;
        this.date = date;
        this.total = total;
        this.oD = oD;
    }

    public Order(int customerID, Date date, float total, List<orderDetail> oD)
    {
        this.id = null;
        this.customerID = customerID;
        this.date = date;
        this.total = total;
        this.oD = oD;
    }

    public static class orderDetail
    {
        int id;
        int productID;
        int qty;
        float unitPrice;

        public orderDetail(int id, int productID, int qty, float unitPrice)
        {
            this.id = id;
            this.productID = productID;
            this.qty = qty;
            this.unitPrice = unitPrice;
        }

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

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

        public float getUnitPrice()
        {
            return unitPrice;
        }

        public void setUnitPrice(float unitPrice)
        {
            this.unitPrice = unitPrice;
        }

    }

    public Integer getId()
    {
        return id;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public Date getDate()
    {
        return date;
    }

    public float getTotal()
    {
        return total;
    }

    public List<orderDetail> getoD()
    {
        return oD;
    }

    public void setoD(List<orderDetail> oD)
    {
        this.oD = oD;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + customerID;
        result = prime * result + id;
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
        Order other = (Order) obj;
        if (customerID != other.customerID)
            return false;
        if (id != other.id)
            return false;
        return true;
    }


    public boolean deeplyEquals(DomainObject obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (customerID != other.customerID)
            return false;
        if (date == null)
        {
            if (other.date != null)
                return false;
        }
        else if (!date.equals(other.date))
            return false;
        if (id != other.id)
            return false;
        if (oD == null)
        {
            if (other.oD != null)
                return false;
        }
        else if (!oD.equals(other.oD))
            return false;
        if (Float.floatToIntBits(total) != Float.floatToIntBits(other.total))
            return false;
        return true;
    }

    public DomainObject clone()
    {

        Order copy;

        try
        {
            copy = (Order) super.clone();
            copy.setoD(this.getoD());
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
        return "Order [id=" + id + ", customerID=" + customerID + ", date=" + date + ", total=" + total + ", oD=" + oD
                + "]";
    }

}
