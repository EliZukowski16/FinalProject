package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;

public abstract class AbstractSales extends AbstractDomainObject implements Sales
{
    private final Product product;
    private final Integer numberSold;
    private final BigDecimal totalValue;
    private final Boolean aggregateSales;
    
    public AbstractSales(Integer id, Product product, Integer numberSold, BigDecimal totalValue, Boolean aggregateSales, Boolean loaded)
    {
        super(id, loaded);
        this.product = product;
        this.numberSold = numberSold;
        this.totalValue = totalValue;
        this.aggregateSales = aggregateSales;
    }
    
    @Override
    public Product getProduct()
    {
        return product;
    }
    
    @Override
    public Integer getNumberSold()
    {
        return numberSold;
    }

    @Override
    public BigDecimal getTotalValue()
    {
        return totalValue;
    }
    
    @Override
    public Boolean getAggregateSales()
    {
        return aggregateSales;
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
        AbstractSales other = (AbstractSales) obj;
        
        if (numberSold == null)
        {
            if (other.numberSold != null)
                return false;
        }
        else if (!numberSold.equals(other.numberSold))
            return false;
        if (product == null)
        {
            if (other.product != null)
                return false;
        }
        else if (!product.equals(other.product))
            return false;
        if (totalValue == null)
        {
            if (other.totalValue != null)
                return false;
        }
        else if (!totalValue.equals(other.totalValue))
            return false;
        if (aggregateSales == null)
        {
            if (other.aggregateSales != null)
                return false;
        }
        else if (!aggregateSales.equals(other.aggregateSales))
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
        SalesDaily other = (SalesDaily) obj;
        if (this.getId() == null)
        {
            if (other.getId() != null)
                return false;
        }
        else if (!this.getId().equals(other.getId()))
            return false;
        return true;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
        return result;
    }
    
    public abstract static class Builder implements Sales.Builder
    {
        protected Integer id;
        protected Boolean loaded;
        protected Product product;
        protected Integer numberSold;
        protected BigDecimal totalValue;
        protected Boolean aggregateSales;

        public Builder()
        {
            
        }

        public Builder(AbstractSales sales)
        {
            this.id = sales.getId();
            this.loaded = sales.isLoaded();
            this.product = sales.getProduct();
            this.numberSold = sales.getNumberSold();
            this.totalValue = sales.getTotalValue();
            this.aggregateSales = sales.getAggregateSales();
        }

        @Override
        public Builder id(Integer id)
        {
            this.id = id;
            return this;
        }

        @Override
        public Builder loaded(Boolean loaded)
        {
            this.loaded = loaded;
            return this;
        }

        @Override
        public Builder product(Product product)
        {
            this.product = product;
            return this;
        }

        @Override
        public Builder numberSold(Integer numberSold)
        {
            this.numberSold = numberSold;
            return this;
        }

        @Override
        public Builder totalValue(BigDecimal totalValue)
        {
            this.totalValue = totalValue;
            return this;
        }

        @Override
        public Builder totalValue(Double totalValue)
        {
            return this.totalValue(BigDecimal.valueOf(totalValue));
        }
        
        
        @Override 
        public Builder aggregateSales(Boolean aggregateSales)
        {
            this.aggregateSales = aggregateSales;
            return this;
        }

        @Override
        public abstract AbstractSales build();

    }

}
