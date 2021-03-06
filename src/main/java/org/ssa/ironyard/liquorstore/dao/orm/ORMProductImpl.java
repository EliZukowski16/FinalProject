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
    
    public AbstractORM<CoreProduct> getCoreProductORM()
    {
        return coreProductORM;
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
        Product product = new Product(id, coreProduct, baseUnit, quantity, inventory, price, true);

        return product;

    }
    
    public Product mapLowInventory(ResultSet results) throws SQLException
    {
        return map(results).of().inventory(results.getInt("total")).build();
    }

    // public Product map(ResultSet results, Integer offset) throws SQLException
    // {
    // Integer id = results.getInt(offset);
    // BaseUnit baseUnit = BaseUnit.getInstance(results.getString(++offset));
    // Integer quantity = results.getInt(++offset);
    // Integer inventory = results.getInt(++offset);
    // BigDecimal price = results.getBigDecimal(++offset);
    // CoreProduct coreProduct = this.mapCoreProduct(results, ++offset);
    // Product product = new Product(id, coreProduct, baseUnit, quantity,
    // inventory, price, true);
    //
    // return product;
    // }

//    private CoreProduct mapCoreProduct(ResultSet results, Integer integer)
//    {
//        // TODO Auto-generated method stub
//        return null;
//    }

    private CoreProduct mapCoreProduct(ResultSet results) throws SQLException
    {
        return this.coreProductORM.map(results);
    }

    @Override
    public String prepareRead()
    {
        String read = " SELECT " + projection() + " , " + coreProductORM.projection() + " FROM "
                + this.coreProductJoin() + " ON " + this.coreProductRelation() + " WHERE " + table() + "."
                + this.primaryKeys.get(0) + " = ? ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read prepared Statement: {}", read);

        return read;
    }

    @Override
    public String prepareReadByIds(Integer numberOfIds)
    {
        String readByIds = " SELECT " + projection() + " , " + coreProductORM.projection() + " FROM "
                + this.coreProductJoin() + " ON " + this.coreProductRelation() + " WHERE " + table() + "."
                + this.primaryKeys.get(0) + " IN ( ";

        for (int i = 0; i < numberOfIds; i++)
        {
            readByIds = readByIds + " ?, ";
        }

        readByIds = readByIds.substring(0, readByIds.length() - 2) + " ) ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read By IDs prepared Statement: {}", readByIds);

        return readByIds;

    }

    @Override
    public String prepareReadAll()
    {
        String readAll = " SELECT " + projection() + " , " + coreProductORM.projection() + " FROM "
                + this.coreProductJoin() + " ON " + this.coreProductRelation();

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read All prepared Statement: {}", readAll);

        return readAll;
    }

    private String coreProductJoin()
    {
        return table() + " JOIN " + coreProductORM.table();
    }

    private String coreProductRelation()
    {
        return table() + "." + this.foreignKeys.get(coreProductORM.table()) + " = " + coreProductORM.table() + "."
                + coreProductORM.getPrimaryKeys().get(0);
    }

    public String prepareProductSearch(Integer tags, Integer types)
    {
        String productSearch = " SELECT " + projection() + " , " + coreProductORM.projection()
                + " , count(*) as matches " + " FROM " + table() + " INNER JOIN " + coreProductORM.table() + " ON "
                + coreProductORM.table() + "." + coreProductORM.primaryKeys.get(0) + " = " + table() + "."
                + this.getForeignKeys().get(coreProductORM.table()) + " INNER JOIN product_tags "
                + " ON product_tags.core_product_id = " + coreProductORM.table() + "."
                + coreProductORM.primaryKeys.get(0) + " WHERE ";

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

        if (tags > 0 && ((types > 0) && (types != Type.values().length)))
        {
            productSearch = productSearch + " AND ";
        }

        if ((types > 0) && (types != Type.values().length))
        {
            productSearch = productSearch + " ( " + coreProductORM.table() + "." + coreProductORM.fields.get(1)
                    + " IN ( ";

            for (int i = 0; i < types; i++)
            {
                productSearch = productSearch + " ?, ";
            }

            productSearch = productSearch.substring(0, productSearch.length() - 2);
            productSearch = productSearch + " ) ) ";
        }

        productSearch = productSearch + " GROUP BY ( " + table() + "." + this.primaryKeys.get(0)
                + " ) ORDER BY matches DESC ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Product Search prepared statement: {}", productSearch);

        return productSearch;

    }

    public String prepareProductSearchByCoreProduct()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public String prepareTopSpiritSellersInLastMonth()
    {
        String topSellers = " (SELECT " + projection() + " , " + coreProductORM.projection()
                + " , sum(order_detail.quantity) as matches " + " FROM product " + " INNER JOIN core_product "
                + " ON core_product.id = product.core_product_id " + " INNER JOIN order_detail "
                + " ON order_detail.product_id = product.id " + " INNER JOIN _order "
                + " ON order_detail.order_id = _order.id " + " WHERE (core_product.type IN ('SPIRITS') "
                + " AND _order.time_order_placed >= DATE_SUB(NOW(), INTERVAL 1 MONTH)) " + " GROUP BY product.id "
                + " ORDER BY matches DESC " + " LIMIT 5) ";

        return topSellers;
    }

    public String prepareTopBeerAndCiderSellersInLastMonth()
    {
        String topSellers = " (SELECT " + projection() + " , " + coreProductORM.projection()
                + " , sum(order_detail.quantity) as matches " + " FROM product " + " INNER JOIN core_product "
                + " ON core_product.id = product.core_product_id " + " INNER JOIN order_detail "
                + " ON order_detail.product_id = product.id " + " INNER JOIN _order "
                + " ON order_detail.order_id = _order.id " + " WHERE (core_product.type IN ('BEER', 'CIDER') "
                + " AND _order.time_order_placed >= DATE_SUB(NOW(), INTERVAL 1 MONTH)) " + " GROUP BY product.id "
                + " ORDER BY matches DESC " + " LIMIT 5) ";

        return topSellers;
    }

    public String prepareTopWineSellersInLastMonth()
    {
        String topSellers = " (SELECT " + projection() + " , " + coreProductORM.projection()
                + " , sum(order_detail.quantity) as matches " + " FROM product " + " INNER JOIN core_product "
                + " ON core_product.id = product.core_product_id " + " INNER JOIN order_detail "
                + " ON order_detail.product_id = product.id " + " INNER JOIN _order "
                + " ON order_detail.order_id = _order.id " + " WHERE (core_product.type IN ('WINE') "
                + " AND _order.time_order_placed >= DATE_SUB(NOW(), INTERVAL 1 MONTH)) " + " GROUP BY product.id "
                + " ORDER BY matches DESC " + " LIMIT 5) ";

        return topSellers;
    }

    public String prepareLowInventory()
    {
        String lowInventory = " SELECT " + projection() + " , " + coreProductORM.projection() +
                " , CASE _order.order_status " +
                " WHEN 'PENDING' THEN " + table() + "." + getFields().get(3) +
                " - SUM( order_detail.quantity) " +
                " ELSE " + table() + "." + getFields().get(3) +
                " END as total " +
                " FROM " +
                this.coreProductJoin() + " ON " + this.coreProductRelation() +
                " LEFT JOIN order_detail " +
                " ON order_detail.product_id = " + table() + "." + this.getPrimaryKeys().get(0) +
                " LEFT JOIN _order " +
                " ON _order.id " +
                " = order_detail.order_id " +
                " GROUP BY " + table() + "." + this.getPrimaryKeys().get(0) +
                " LIMIT ? ";
       
        return lowInventory;
    }

}
