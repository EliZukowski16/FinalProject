package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class SalesWeekly extends AbstractSales implements Sales
{
    private final Integer weekSold;

    public SalesWeekly(Integer id, Product product, Integer numberSold, BigDecimal totalValue, LocalDate dateSold,
            Boolean aggregateSales, Boolean loaded)
    {
        super(id, product, numberSold, totalValue, aggregateSales, loaded);

        WeekFields woy = WeekFields.of(Locale.US);
        TemporalField tf = woy.weekOfWeekBasedYear();
        this.weekSold = dateSold.get(tf);
    }

    public SalesWeekly(Integer id, Product product, Integer numberSold, BigDecimal totalValue, Integer weekSold,
            Boolean aggregateSales, Boolean loaded)
    {
        super(id, product, numberSold, totalValue, aggregateSales, loaded);
        this.weekSold = weekSold;
    }

    public Integer getWeekSold()
    {
        return weekSold;
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
        SalesWeekly other = (SalesWeekly) obj;
        if (weekSold == null)
        {
            if (other.weekSold != null)
                return false;
        }
        else if (!weekSold.equals(other.weekSold))
            return false;
        return true;
    }

    @Override
    public SalesWeekly clone()
    {
        return (SalesWeekly) this.of().product(this.getProduct()).build();
    }

    @Override
    public Builder of()
    {
        return new Builder(this);
    }

    public static class Builder extends AbstractSales.Builder implements Sales.Builder
    {
        private Integer weekSold;

        public Builder()
        {
            super();
        }

        public Builder(SalesWeekly sales)
        {
            super(sales);
            this.weekSold = sales.getWeekSold();
        }

        public Builder weekSold(LocalDate dateSold)
        {
            WeekFields woy = WeekFields.of(Locale.US);
            TemporalField tf = woy.weekOfWeekBasedYear();

            this.weekSold = dateSold.get(tf);
            return this;
        }

        public Builder weekSold(Integer weekSold)
        {
            this.weekSold = weekSold;
            return this;
        }

        @Override
        public SalesWeekly build()
        {
            return new SalesWeekly(this.id, this.product, this.numberSold, this.totalValue, this.weekSold,
                    this.aggregateSales, this.loaded);
        }
    }

}
