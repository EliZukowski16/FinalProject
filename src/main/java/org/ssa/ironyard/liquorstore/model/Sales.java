package org.ssa.ironyard.liquorstore.model;

import java.math.BigDecimal;

public interface Sales extends DomainObject
{
    public Product getProduct();

    public Integer getNumberSold();

    public BigDecimal getTotalValue();

    @Override
    public boolean deeplyEquals(DomainObject obj);

    public static interface Builder
    {
        public Builder id(Integer id);

        public Builder loaded(Boolean loaded);

        public Builder product(Product product);

        public Builder numberSold(Integer numberSold);

        public Builder totalValue(BigDecimal totalValue);

        public Builder totalValue(Double totalValue);

        public Sales build();

    }

}
