package org.ssa.ironyard.liquorstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.ssa.ironyard.liquorstore.dao.orm.ORMProductImpl;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

@Repository
public class DAOProductImpl extends AbstractDAOProduct implements DAOProduct
{
    @Autowired
    public DAOProductImpl(DataSource dataSource)
    {
        super(new ORMProductImpl(), dataSource);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Product domainToInsert) throws SQLException
    {
        insertStatement.setInt(1, domainToInsert.getCoreProduct().getId());
        insertStatement.setString(2, domainToInsert.getBaseUnit().toString());
        insertStatement.setInt(3, domainToInsert.getQuantity());
        insertStatement.setInt(4, domainToInsert.getInventory());
        insertStatement.setBigDecimal(5, domainToInsert.getPrice());

    }

    @Override
    protected Product afterInsert(Product copy, Integer id)
    {
        Product product;
        product = new Product(id, copy.getCoreProduct(), copy.getBaseUnitType(), copy.getQuantity(),
                copy.getInventory(), copy.getPrice(), true);

        return product;

    }

    @Override
    protected Product afterUpdate(Product copy)
    {
        Product product;
        product = new Product(copy.getId(), copy.getCoreProduct(), copy.getBaseUnitType(), copy.getQuantity(),
                copy.getInventory(), copy.getPrice(), true);
        return product;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Product domainToUpdate)
    {
        return new PreparedStatementSetter()
        {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                ps.setInt(1, domainToUpdate.getCoreProduct().getId());
                ps.setString(2, domainToUpdate.getBaseUnit().toString());
                ps.setInt(3, domainToUpdate.getQuantity());
                ps.setInt(4, domainToUpdate.getInventory());
                ps.setBigDecimal(5, domainToUpdate.getPrice());
                ps.setInt(6, domainToUpdate.getId());

            }

        };

    }

    @Override
    public List<Product> searchProducts(List<Tag> tags, List<Type> types)
    {

        if (tags.size() == 0 && types.size() == 0)
            return new ArrayList<>();
        return this.springTemplate.query((Connection conn) ->
        {
            PreparedStatement statement = conn
                    .prepareStatement(((ORMProductImpl) this.orm).prepareProductSearch(tags.size(), types.size()));
            productSearchPreparer(statement, tags, types);
            return statement;

        }, this.listExtractor);

    }

    private void productSearchPreparer(PreparedStatement searchStatement, List<Tag> tags, List<Type> types)
            throws SQLException
    {
        for (int i = 1; i <= tags.size(); i++)
        {
            searchStatement.setString(i, tags.get(i - 1).getName() + "%");
        }

        if (types.size() != Type.values().length)
        {
            for (int i = tags.size() + 1; i <= types.size() + tags.size(); i++)
            {
                searchStatement.setString(i, types.get(i - tags.size() - 1).toString());
            }
        }

    }

    @Override
    public List<Product> readProductsByCoreProduct(Integer coreProductID)
    {
        return null;
    }

    @Override
    public List<Product> readProductsByCoreProducts(List<Integer> coreProductIDs)
    {
        if (coreProductIDs.size() == 0)
            return new ArrayList<>();

        return this.springTemplate.query(((ORMProductImpl) this.orm).prepareProductSearchByCoreProduct(),
                (PreparedStatement ps) ->
                {
                    for (int i = 0; i < coreProductIDs.size(); i++)
                    {
                        ps.setInt(i + 1, coreProductIDs.get(i));
                    }

                }, this.listExtractor);
    }

    @Override
    public List<Product> readByUnitAndQuantity(BaseUnit baseUnit)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> readByUnitAndQuantity(Integer quantity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Product> readByUnitAndQuantity(BaseUnit baseUnit, Integer quantity)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
