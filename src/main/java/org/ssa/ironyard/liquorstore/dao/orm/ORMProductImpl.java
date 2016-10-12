package org.ssa.ironyard.liquorstore.dao.orm;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Product.BaseUnit;

public class ORMProductImpl extends AbstractORM<Product> implements ORM<Product>
{
    AbstractORM<CoreProduct> coreProductORM;

    static Logger LOGGER = LogManager.getLogger(ORMProductImpl.class);

    public ORMProductImpl()
    {
        coreProductORM = new ORMCoreProductImpl();

        this.primaryKeys.add("id");

        this.fields.add("core_product_id");
        this.fields.add("base_unit");
        this.fields.add("quantity");
        this.fields.add("inventory");
        this.fields.add("price");

        this.foreignKeys.put("core_product", "core_product_id");
    }

    @Override
    public String table()
    {
        return "product";
    }

    @Override
    public Product map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt(table() + ".id");
        BaseUnit baseUnit = BaseUnit.getInstance(results.getString(table() + ".base_unit"));
        Integer quantity = results.getInt(table() + ".quantity");
        Integer inventory = results.getInt(table() + ".inventory");
        BigDecimal price = results.getBigDecimal(table() + ".price");
        CoreProduct coreProduct = this.mapCoreProduct(results);
        Product product = new Product(id, coreProduct, baseUnit, quantity, inventory, price);

        product.setLoaded(true);

        return product;

    }

    private CoreProduct mapCoreProduct(ResultSet results) throws SQLException
    {
        return coreProductORM.map(results);
    }

    @Override
    public String prepareRead()
    {
        String read = " SELECT " + this.projection() + " , " + coreProductORM.projection() + " FROM "
                + this.coreProductJoin() + " ON " + this.coreProductRelation() + " WHERE " + this.table() + "."
                + this.primaryKeys.get(0) + " = ? ";

        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Read prepared Statement: {}", read);

        return read;
    }
    
    @Override
    public String prepareReadByIds(Integer numberOfIds)
    {
        String readByIds = " SELECT " + this.projection() + " , " + coreProductORM.projection() + " FROM "
                + this.coreProductJoin() + " ON " + this.coreProductRelation() + " WHERE " + this.table() + "."
                + this.primaryKeys.get(0) + " IN ( ";
        
        for(int i = 0; i < numberOfIds; i++)
        {
            readByIds = readByIds + " ?, ";
        }
        
        readByIds = readByIds.substring(0, readByIds.length() - 2) + " ) ";
        
        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Read By IDs prepared Statement: {}", readByIds);
        
        return readByIds;
                
    }
    
    @Override
    public String prepareReadAll()
    {
        String readAll = " SELECT " + this.projection() + " , " + coreProductORM.projection() + " FROM "
                + this.coreProductJoin() + " ON " + this.coreProductRelation();
        
        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Read All prepared Statement: {}", readAll);
        
        return readAll;
    }

    private String coreProductJoin()
    {
        return this.table() + " JOIN " + coreProductORM.table();
    }

    private String coreProductRelation()
    {
        return this.table() + "." + this.foreignKeys.get(coreProductORM.table()) + " = " + coreProductORM.table() + "."
                + coreProductORM.getPrimaryKeys().get(0);
    }

    public String prepareProductSearch(Integer tags, Integer types)
    {
        String productSearch = " SELECT " + this.projection() + " , " + coreProductORM.projection() + " , count(*) as matches " +
                " FROM " + this.table() +
                " INNER JOIN " + coreProductORM.table() + 
                " ON " + coreProductORM.table() + "." + coreProductORM.primaryKeys.get(0) + 
                " = " + this.table() + "." + this.getForeignKeys().get(coreProductORM.table()) +     
                " INNER JOIN product_tags " +
                " ON product_tags.core_product_id = " + coreProductORM.table() + "." + coreProductORM.primaryKeys.get(0) + 
                " WHERE ";

        if (tags > 0)
        {
            productSearch = productSearch + " ( ";

            for (int i = 0; i < tags; i++)
            {
                productSearch = productSearch + " product_tags.name LIKE ? OR ";
            }
            
            productSearch = productSearch.substring(0, productSearch.length() - 3);
            productSearch = productSearch + " ) ";
        }
        
        if(tags > 0 && ((types > 0) && (types != Type.values().length)))
        {
            productSearch = productSearch + " AND ";
        }
        
        if((types > 0) && (types != Type.values().length))
        {
            productSearch = productSearch + " ( " + coreProductORM.table() + "." + coreProductORM.fields.get(1) + " IN ( ";
            
            for(int i = 0; i < types; i++)
            {
                productSearch = productSearch + " ?, ";
            }
            
            productSearch = productSearch.substring(0, productSearch.length() - 2);
            productSearch = productSearch + " ) ) ";
        }
        
        productSearch = productSearch + " GROUP BY ( " + this.table() + "." + this.primaryKeys.get(0) +
                " ) ORDER BY matches DESC ";
        
        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Product Search prepared statement: {}", productSearch);
        
        return productSearch;

    }

}
