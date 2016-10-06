package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order extends AbstractDomainObject implements DomainObject
{
    private Customer customer;
    private final LocalDateTime date;
    private final BigDecimal total;
    private List<OrderDetail> oD;

    public Order(Integer id, Customer customer, LocalDateTime date, BigDecimal total, List<OrderDetail> oD)
    {
        super(id);
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.oD = oD;
    }

    public Order(Customer customer, LocalDateTime date, BigDecimal total, List<OrderDetail> oD)
    {
        this(null, customer, date, total, oD);
    }
    
    public Order(Customer customer, LocalDateTime date, BigDecimal total)
    {   
        this(customer, date, total, new ArrayList<>());
    }

    public static class OrderDetail
    {
        Product product;
        Integer qty;
        BigDecimal unitPrice;

        public OrderDetail(Product product, Integer qty, BigDecimal unitPrice)
        {
            this.product = product;
            this.qty = qty;
            this.unitPrice = unitPrice;
        }


        public Product getProduct()
        {
            return product;
        }

        public void setProduct(Product product)
        {
            this.product = product;
        }

        public int getQty()
        {
            return qty;
        }

        public void setQty(int qty)
        {
            this.qty = qty;
        }

        public BigDecimal getUnitPrice()
        {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice)
        {
            this.unitPrice = unitPrice;
        }


        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((product == null) ? 0 : product.hashCode());
            result = prime * result + ((qty == null) ? 0 : qty.hashCode());
            result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
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
            OrderDetail other = (OrderDetail) obj;
            if (product == null)
            {
                if (other.product != null)
                    return false;
            }
            else if (!product.equals(other.product))
                return false;
            if (qty == null)
            {
                if (other.qty != null)
                    return false;
            }
            else if (!qty.equals(other.qty))
                return false;
            if (unitPrice == null)
            {
                if (other.unitPrice != null)
                    return false;
            }
            else if (!unitPrice.equals(other.unitPrice))
                return false;
            return true;
        }
        
        
    }
    
    public void addOrderDetail(OrderDetail oD)
    {
        this.oD.add(oD);
    }

    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public BigDecimal getTotal()
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
        if (customer != other.customer)
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
        if (this.total.compareTo(other.total) != 0)
            return false;
        return true;
    }

    public Order clone()
    {

        Order copy;

        copy = (Order) super.clone();
        copy.setoD(this.getoD());
        copy.setCustomer(this.getCustomer());
        return copy;

    }

    @Override
    public String toString()
    {
        return "Order [id=" + this.getId() + ", customerID=" + customer + ", date=" + date + ", total=" + total
                + ", oD=" + oD + "]";
    }

}
