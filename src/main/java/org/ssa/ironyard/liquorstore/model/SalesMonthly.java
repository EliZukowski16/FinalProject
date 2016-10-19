package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesMonthly extends AbstractSales implements Sales
{
    private final Integer monthSold;

    public SalesMonthly(Integer id, Product product, Integer numberSold, BigDecimal totalValue, LocalDate dateSold,
            Boolean aggregateSales, Boolean loaded)
    {
        super(id, product, numberSold, totalValue, aggregateSales, loaded);

        this.monthSold = dateSold.getMonthValue();
    }

    public SalesMonthly(Integer id, Product product, Integer numberSold, BigDecimal totalValue, Integer monthSold,
            Boolean aggregateSales, Boolean loaded)
    {
        super(id, product, numberSold, totalValue, aggregateSales, loaded);
        this.monthSold = monthSold;
    }

    public Integer getMonthSold()
    {
        return monthSold;
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
        SalesMonthly other = (SalesMonthly) obj;
        if (monthSold == null)
        {
            if (other.monthSold != null)
                return false;
        }
        else if (!monthSold.equals(other.monthSold))
            return false;
        return true;
    }

    @Override
    public SalesMonthly clone()
    {
        return (SalesMonthly) this.of().product(this.getProduct()).build();
    }

    @Override
    public Builder of()
    {
        return new Builder(this);
    }

    public static class Builder extends AbstractSales.Builder implements Sales.Builder
    {
        private Integer monthSold;

        public Builder()
        {
            super();
        }

        public Builder(SalesMonthly sales)
        {
            super(sales);
            this.monthSold = sales.getMonthSold();
        }

        public Builder monthSold(LocalDate dateSold)
        {
            this.monthSold = dateSold.getMonthValue();
            return this;
        }

        public Builder monthSold(Integer monthSold)
        {
            this.monthSold = monthSold;
            return this;
        }

        @Override
        public SalesMonthly build()
        {
            return new SalesMonthly(this.id, this.product, this.numberSold, this.totalValue, this.monthSold,
                    this.aggregateSales, this.loaded);
        }
    }

}
