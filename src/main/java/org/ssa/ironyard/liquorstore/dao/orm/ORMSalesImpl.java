package org.ssa.ironyard.liquorstore.dao.orm;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.SalesDaily.Builder;

public class ORMSalesImpl extends AbstractORM<Sales> implements ORM<Sales>
{

    Logger LOGGER = LogManager.getLogger(ORMSalesImpl.class);

    AbstractORM<Product> productORM;

    public ORMSalesImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("product_id");
        this.fields.add("number");
        this.fields.add("total_value");
        this.fields.add("date_sold");
        this.fields.add("aggregate_sales");

        productORM = new ORMProductImpl();
    }

    @Override
    public String table()
    {
        return "sales";

    }

    @Override
    public Sales map(ResultSet results) throws SQLException
    {
        Product product = productORM.map(results);

        Integer id;
        Integer numberSold;
        BigDecimal totalValue;
        LocalDate dateSold;
        Boolean aggregateSales;

        if (results.getInt(table() + ".id") == 0)
        {
            id = 0;
            numberSold = 0;
            totalValue = BigDecimal.ZERO;
            dateSold = LocalDate.now();
            aggregateSales = false;
        }
        else
        {
            id = results.getInt(table() + ".id");
            numberSold = results.getInt(table() + ".number");
            totalValue = results.getBigDecimal(table() + ".total_value");
            dateSold = results.getDate(table() + ".date_sold").toLocalDate();
            aggregateSales = results.getBoolean(table() + ".aggregate_sales");
        }

        return ((Builder) new SalesDaily.Builder().id(id).product(product).numberSold(numberSold)
                .totalValue(totalValue)).dateSold(dateSold).aggregateSales(aggregateSales).loaded(true).build();
    }

    private String buildEager()
    {
        String eager = buildEagerSelect() + " FROM " + table() + " " + buildProductJoin() + buildCoreProductJoin();

        return eager;
    }

    private String buildEagerLeft()
    {
        String eager = buildEagerSelect() + " FROM " + table() + " RIGHT " + buildProductJoin() 
                + buildCoreProductJoin();

        return eager;
    }

    private String buildEagerSelect()
    {
        String select = " SELECT " + projection() + ", " + this.productORM.projection() + " , "
                + ((ORMProductImpl) this.productORM).getCoreProductORM().projection();

        return select;
    }

    private String buildProductJoin()
    {
        String join = " JOIN " + this.productORM.table() + " " + buildProductRelation();
        return join;
    }

    private String buildProductRelation()
    {
        String relation = " ON " + this.productORM.table() + "." + this.productORM.getPrimaryKeys().get(0) + " = "
                + table() + "." + getFields().get(0) + " ";

        return relation;
    }

    private String buildCoreProductJoin()
    {
        String join = " JOIN " + ((ORMProductImpl) this.productORM).getCoreProductORM().table() + " "
                + buildCoreProductRelation();

        return join;
    }

    private String buildCoreProductRelation()
    {
        String relation = " ON " + this.productORM.table() + "." + this.productORM.getFields().get(0) + " = "
                + ((ORMProductImpl) this.productORM).getCoreProductORM().table() + "."
                + ((ORMProductImpl) this.productORM).getCoreProductORM().getPrimaryKeys().get(0) + " ";

        return relation;
    }

    @Override
    public String prepareReadAll()
    {
        String readAll = buildEager();

        return readAll;
    }

    @Override
    public String prepareReadByIds(Integer numberOfIds)
    {
        String readByIDs = buildEager() + " WHERE " + table() + "." + getPrimaryKeys().get(0) + " IN ( ";

        for (int i = 0; i < numberOfIds; i++)
        {
            readByIDs = readByIDs + " ?, ";
        }

        readByIDs = readByIDs.substring(0, readByIDs.length() - 2) + " ) ";

        return readByIDs;
    }

    public String prepareReadSalesForProducts(Integer numberOfProducts)
    {
        String productSales = buildEagerLeft() + " WHERE " + table() + "." + getFields().get(0) + " IN ( ";
        for (int i = 0; i < numberOfProducts; i++)
        {
            productSales = productSales + " ?, ";
        }

        productSales = productSales.substring(0, productSales.length() - 2) + " ) ";

        return productSales;
    }

    public String prepareReadSalesForLastVariableDays(Integer numberOfProducts)
    {
        String lastVariableDays = buildEagerLeft() + " WHERE " + table() + "." + getFields().get(3)
                + " <= DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND " + table() + "." + getFields().get(3)
                + " > DATE_SUB(CURDATE(), INTERVAL ? DAY) ";

        if (numberOfProducts > 0)
        {
            lastVariableDays = lastVariableDays + " AND " + table() + "." + getFields().get(0) + " IN ( ";

            for (int i = 0; i < numberOfProducts; i++)
            {
                lastVariableDays = lastVariableDays + " ?, ";
            }

            lastVariableDays = lastVariableDays.substring(0, lastVariableDays.length() - 2) + " ) ";
        }

        return lastVariableDays;
    }

    public String prepareReadInTimeFrame()
    {
        String readInTimeFrame = buildEager() + " WHERE ( " + table() + "." + getFields().get(3)
                + " BETWEEN ? AND ? ) ";

        return readInTimeFrame;
    }

    public String topSellers()
    {
        String topSellersSubQueryAlias = " SS ";
        String topSellersSubQuery = "( SELECT " + table() + "." + getFields().get(0) + " FROM " + table() + " WHERE "
                + table() + "." + getFields().get(3) + " > DATE_SUB(NOW(), INTERVAL ? DAY) " + " GROUP BY " + table()
                + "." + getFields().get(0) + " ORDER BY SUM( " + table() + "." + getFields().get(1) + " ) "
                + " LIMIT ? ) ";

        String topSellers = buildEager() + " JOIN " + topSellersSubQuery + " AS " + topSellersSubQueryAlias + " ON "
                + table() + "." + getFields().get(0) + " = " + topSellersSubQueryAlias + "." + getFields().get(0);

        return topSellers;
    }

    public String prepareProductSearch(Integer tags, Integer types)
    {
        String productSearch = buildEagerLeft() + " JOIN product_tags ON product_tags.core_product_id = core_product.id "+ " WHERE ";

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
            productSearch = productSearch + " ( " + ((ORMProductImpl) this.productORM).getCoreProductORM().table() + "."
                    + ((ORMProductImpl) this.productORM).getCoreProductORM().getFields().get(1)
                    + " IN ( ";

            for (int i = 0; i < types; i++)
            {
                productSearch = productSearch + " ?, ";
            }

            productSearch = productSearch.substring(0, productSearch.length() - 2);
            productSearch = productSearch + " ) ) ";
        }

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Product Search prepared statement: {}", productSearch);

        return productSearch;

    }

}
