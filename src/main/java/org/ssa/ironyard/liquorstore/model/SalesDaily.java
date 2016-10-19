package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesDaily extends AbstractSales implements Sales
{
    private final LocalDate dateSold;

    public SalesDaily(Integer id, Product product, Integer numberSold, BigDecimal totalValue, LocalDate dateSold,
            Boolean aggregateSales, Boolean loaded)
    {
        super(id, product, numberSold, totalValue, aggregateSales, loaded);
        this.dateSold = dateSold;
    }

    public LocalDate getDateSold()
    {
        return dateSold;
    }

    @Override
    public boolean deeplyEquals(DomainObject obj)
    {

        if (!super.deeplyEquals(obj))
            return false;
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SalesDaily other = (SalesDaily) obj;
        if (dateSold == null)
        {
            if (other.dateSold != null)
                return false;
        }
        else if (!dateSold.equals(other.dateSold))
            return false;
        return true;
    }

    @Override
    public SalesDaily clone()
    {
        return (SalesDaily) this.of().product(this.getProduct()).build();
    }
    
    @Override
    public Builder of()
    {
        return new Builder(this);
    }

    public static class Builder extends AbstractSales.Builder implements Sales.Builder
    {
        private LocalDate dateSold;

        public Builder()
        {
            super();
        }

        public Builder(SalesDaily sales)
        {
            super(sales);
            this.dateSold = sales.getDateSold();
        }

        public Builder dateSold(LocalDate dateSold)
        {
            this.dateSold = dateSold;
            return this;
        }

        @Override
        public SalesDaily build()
        {
            return new SalesDaily(this.id, this.product, this.numberSold, this.totalValue, this.dateSold,
                    this.aggregateSales, this.loaded);
        }
    }

}
