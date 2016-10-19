package org.ssa.ironyard.liquorstore.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.ssa.ironyard.liquorstore.dao.orm.ORMSalesImpl;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;

public class DAOSalesImpl extends AbstractDAOSales implements DAOSales
{

    protected DAOSalesImpl(DataSource dataSource)
    {
        super(new ORMSalesImpl(), dataSource);
    }

    @Override
    public List<Sales> readSalesForProduct(Integer productID)
    {
        List<Integer> productIDs = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();
        productIDs.add(productID);

        return this.readSalesForProduct(productIDs);
    }

    @Override
    public List<Sales> readSalesForProduct(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.springTemplate.query(((ORMSalesImpl) this.orm).prepareReadSalesForProducts(productIDs.size()),
                (PreparedStatement ps) ->
                {
                    for (int i = 0; i < productIDs.size(); i++)
                    {
                        ps.setInt(i + 1, productIDs.get(i));
                    }
                }, this.listExtractor);
    }

    private List<Sales> readSalesForLastVariableDays(Integer numberOfDays, List<Integer> productIDs)
    {
        return this.springTemplate.query(
                ((ORMSalesImpl) this.orm).prepareReadSalesForLastVariableDays(productIDs.size()),
                (PreparedStatement ps) ->
                {
                    ps.setInt(1, numberOfDays);
                    if (!productIDs.isEmpty())
                    {
                        for (int i = 0; i < productIDs.size(); i++)
                            ps.setInt(i + 2, productIDs.get(i));
                    }

                }, this.listExtractor);
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Sales domainToInsert) throws SQLException
    {
        insertStatement.setInt(1, domainToInsert.getProduct().getId());
        insertStatement.setInt(2, domainToInsert.getNumberSold());
        insertStatement.setBigDecimal(3, domainToInsert.getTotalValue());
        insertStatement.setDate(4, Date.valueOf(((SalesDaily) domainToInsert).getDateSold()));

    }

    @Override
    protected Sales afterInsert(Sales copy, Integer id)
    {
        return ((SalesDaily) copy).of().id(id).loaded(true).build();
    }

    @Override
    protected Sales afterUpdate(Sales copy)
    {
        return ((SalesDaily) copy).of().loaded(true).build();
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Sales domainToUpdate)
    {
        return new PreparedStatementSetter()
        {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                ps.setInt(1, domainToUpdate.getProduct().getId());
                ps.setInt(2, domainToUpdate.getNumberSold());
                ps.setBigDecimal(3, domainToUpdate.getTotalValue());
                ps.setDate(4, Date.valueOf(((SalesDaily) domainToUpdate).getDateSold()));
                ps.setInt(5, domainToUpdate.getId());

            }
        };

    }

    @Override
    public List<Sales> readSalesForLast30Days()
    {
        return this.readSalesForLastVariableDays(30, new ArrayList<>());
    }

    @Override
    public List<Sales> readSalesForLast30Days(Integer productID)
    {
        List<Integer> productIDs = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();
        productIDs.add(productID);

        return this.readSalesForLast30Days(productIDs);

    }

    @Override
    public List<Sales> readSalesForLast30Days(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.readSalesForLastVariableDays(30, productIDs);

    }

    @Override
    public List<Sales> readSalesForLast90Days()
    {
        return this.readSalesForLastVariableDays(90, new ArrayList<>());

    }

    @Override
    public List<Sales> readSalesForLast90Days(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.readSalesForLastVariableDays(90, productIDs);
    }

    @Override
    public List<Sales> readSalesForLast90Days(Integer productID)
    {
        List<Integer> productIDs = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();
        productIDs.add(productID);

        return this.readSalesForLast90Days(productIDs);
    }

    @Override
    public List<Sales> readSalesForLast180Days()
    {
        return this.readSalesForLastVariableDays(180, new ArrayList<>());
    }

    @Override
    public List<Sales> readSalesForLast180Days(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.readSalesForLastVariableDays(180, productIDs);
    }

    @Override
    public List<Sales> readSalesForLast180Days(Integer productID)
    {
        List<Integer> productIDs = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();
        productIDs.add(productID);

        return this.readSalesForLast90Days(productIDs);
    }

    @Override
    public List<Sales> readSalesForLastYear()
    {
        return this.readSalesForLastVariableDays(365, new ArrayList<>());
    }

    @Override
    public List<Sales> readSalesForLastYear(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.readSalesForLastVariableDays(365, productIDs);
    }

    @Override
    public List<Sales> readSalesForLastYear(Integer productID)
    {
        List<Integer> productIDs = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();
        productIDs.add(productID);

        return this.readSalesForLastYear(productIDs);
    }

    @Override
    public List<Sales> readSalesForPreviousDay()
    {
        return this.readSalesForLastVariableDays(1, new ArrayList<>());
    }

    @Override
    public List<Sales> readSalesForPreviousDay(List<Integer> productIDs)
    {
        if (productIDs.isEmpty())
            return new ArrayList<>();

        return this.readSalesForLastVariableDays(1, productIDs);
    }

    @Override
    public List<Sales> readSalesForPreviousDay(Integer productID)
    {
        List<Integer> productIDs = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();
        productIDs.add(productID);

        return this.readSalesForPreviousDay(productIDs);
    }

}
