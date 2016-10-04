package org.ssa.ironyard.liquorstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends AbstractDomainObject implements DomainObject
{
    private final Integer customerID;
    private final LocalDateTime date;
    private final Float total;
    private List<OrderDetail> oD = new ArrayList<>();

    public Order(Integer id, Integer customerID, LocalDateTime date, Float total, List<OrderDetail> oD)
    {
        super(id);
        this.customerID = customerID;
        this.date = date;
        this.total = total;
        this.oD = oD;
    }

    public Order(int customerID, LocalDateTime date, Float total, List<OrderDetail> oD)
    {
        this(null, customerID, date, total, oD);
    }

    public static class OrderDetail
    {
        Integer orderID;
        Integer productID;
        Integer qty;
        Float unitPrice;

        public OrderDetail(Integer orderID, Integer productID, Integer qty, Float unitPrice)
        {
            this.orderID = orderID;
            this.productID = productID;
            this.qty = qty;
            this.unitPrice = unitPrice;
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

    public int getCustomerID()
    {
        return customerID;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public float getTotal()
    {
        return total;
    }

    public List<OrderDetail> getoD()
    {
        return oD;
    }

    public void setoD(List<OrderDetail> oD)
    {
        this.oD = oD;
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
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        if (this.getId() != other.getId())
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
        if (this.getId() != other.getId())
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
        return "Order [id=" + this.getId() + ", customerID=" + customerID + ", date=" + date + ", total=" + total
                + ", oD=" + oD + "]";
    }

}
