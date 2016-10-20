package org.ssa.ironyard.liquorstore.dao.orm;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Product;

public class ORMOrderImpl extends AbstractORM<Order> implements ORM<Order>
{
    static Logger LOGGER = LogManager.getLogger(ORMOrderImpl.class);

    AbstractORM<Customer> customerORM;
    AbstractORM<Product> productORM;
    AbstractORM<CoreProduct> coreProductORM;

    public ORMOrderImpl()
    {
        this.primaryKeys.add("id");

        this.fields.add("customer_id");
        this.fields.add("date");
        this.fields.add("order_status");
        this.fields.add("total");
        this.fields.add("time_order_placed");

        this.foreignKeys.put("customer", "customer_id");

        customerORM = new ORMCustomerImpl();
        productORM = new ORMProductImpl();
        coreProductORM = new ORMCoreProductImpl();
    }

    @Override
    public String table()
    {
        return "_order";
    }

    @Override
    public Order map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt(table() + ".id");
        BigDecimal total = results.getBigDecimal(table() + ".total");
        LocalDateTime date = results.getTimestamp(table() + ".date").toLocalDateTime();
        OrderStatus status = OrderStatus.getInstance(results.getString(table() + ".order_status").toLowerCase());
        LocalDateTime timeOfOrder = results.getTimestamp(table() + ".time_order_placed").toLocalDateTime();

        Customer customer = customerORM.map(results);

        List<OrderDetail> oD = new ArrayList<>();

        oD.add(this.mapOrderDetail(results));

        Order order = new Order(id, customer, date, total, oD, status, timeOfOrder, true);


        return order;
    }

    private OrderDetail mapOrderDetail(ResultSet results) throws SQLException
    {
        Product product = productORM.map(results);
        Integer qty = results.getInt("order_detail.quantity");
        BigDecimal unitPrice = results.getBigDecimal("order_detail.unit_price");

        OrderDetail oD = new OrderDetail(product, qty, unitPrice);

        return oD;
    }

    public String prepareInsertDetail()
    {
        String detailInsert = " INSERT INTO order_detail (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Insert Details prepared Statement: {}", detailInsert);

        return detailInsert;
    }

    private String buildEagerRead()
    {
        return " SELECT " + projection() + " , " + customerORM.projection() + " , " + coreProductORM.projection()
                + " , " + productORM.projection() + " , " + " order_detail.* " + " FROM " + table() +

                " " + this.customerJoin() + " ON " + this.customerRelation() + " " +

                " " + this.orderDetailJoin() + " ON " + this.orderDetailRelation() +

                " " + this.productJoin() + " ON " + this.productRelation() +

                " " + this.coreProductJoin() + " ON " + this.coreProductRelation();
    }
    
    private String buildEagerReadLessCustomer()
    {
        return " SELECT " + projection() + " , "  + coreProductORM.projection()
        + " , " + productORM.projection() + " , " + " order_detail.* " + " FROM " + table() +

        " " + this.orderDetailJoin() + " ON " + this.orderDetailRelation() +

        " " + this.productJoin() + " ON " + this.productRelation() +

        " " + this.coreProductJoin() + " ON " + this.coreProductRelation();
    }

    @Override
    public String prepareRead()
    {
        String read = buildEagerRead() +

                " WHERE " + table() + "." + this.primaryKeys.get(0) + " = ? ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read prepared Statement: {}", read);

        return read;
    }

    @Override
    public String prepareReadAll()
    {
        String readAll = buildEagerRead();

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read All prepared Statement: {}", readAll);

        return readAll;
    }

    @Override
    public String prepareReadByIds(Integer numberOfIds)
    {
        String readByIds = buildEagerRead() + " WHERE " + table() + "." + this.getPrimaryKeys().get(0) + " IN ( ";

        for (int i = 0; i < numberOfIds; i++)
        {
            readByIds = readByIds + " ?, ";
        }

        readByIds = readByIds.substring(0, readByIds.length() - 2) + " ) ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read By IDs prepared Statement: {}", readByIds);

        return readByIds;
    }

    private String customerJoin()
    {
        return " INNER JOIN " + customerORM.table();
    }

    private String customerRelation()
    {
        return table() + "." + this.foreignKeys.get(customerORM.table()) + " = " + customerORM.table() + "."
                + customerORM.getPrimaryKeys().get(0);
    }

    private String coreProductJoin()
    {
        return " INNER JOIN " + coreProductORM.table();
    }

    private String coreProductRelation()
    {
        return productORM.table() + "." + productORM.getForeignKeys().get(coreProductORM.table()) + " = "
                + coreProductORM.table() + "." + coreProductORM.getPrimaryKeys().get(0);
    }

    private String productJoin()
    {
        return " INNER JOIN " + productORM.table();
    }

    private String productRelation()
    {
        return productORM.table() + "." + productORM.getPrimaryKeys().get(0) + " = " + " order_detail.product_id";
    }

    private String orderDetailJoin()
    {
        return "INNER JOIN order_detail ";
    }

    private String orderDetailRelation()
    {
        return table() + "." + this.primaryKeys.get(0) + " = order_detail.order_id ";
    }

    public String prepareReadByCustomers(int numberOfIds)
    {
        String readByCustomerIds = buildEagerRead() + " WHERE " + table() + "." + getFields().get(0)
                + " IN ( ";

        for (int i = 0; i < numberOfIds; i++)
        {
            readByCustomerIds = readByCustomerIds + " ?, ";
        }

        readByCustomerIds = readByCustomerIds.substring(0, readByCustomerIds.length() - 2) + " ) ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read By Customer IDs prepared Statement: {}", readByCustomerIds);

        return readByCustomerIds;
    }

    public String prepareReadInTimeFrame()
    {
        String readInTimeFrame = buildEagerRead() + " WHERE " + table() + "."
                + getFields().get(1) + " BETWEEN ? AND ? ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read In Time Frame prepared Statement: {}", readInTimeFrame);

        return readInTimeFrame;
    }

    public String prepareReadMostRecent()
    {
        String readMostRecent = buildEagerRead() + " ORDER BY " + table() + "." + getFields().get(4) + " LIMIT ? ";


        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read Most Recent prepared Statement: {}", readMostRecent);

        return readMostRecent;
    }

    public String prepareReadByProducts(int numberOfIds)
    {
        String readByProductIds = buildEagerRead() + " WHERE " + productORM.table() + "."
                + productORM.getPrimaryKeys().get(0) + " IN ( ";

        for (int i = 0; i < numberOfIds; i++)
        {
            readByProductIds = readByProductIds + " ?, ";
        }

        readByProductIds = readByProductIds.substring(0, readByProductIds.length() - 2) + " ) ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read By Product IDs prepared Statement: {}", readByProductIds);

        return readByProductIds;
    }

    public String prepareReadByCoreProducts(int numberOfIds)
    {
        String readByCoreProductIds = buildEagerRead() + " WHERE " + coreProductORM.table() + "."
                + coreProductORM.getPrimaryKeys().get(0) + " IN ( ";

        for (int i = 0; i < numberOfIds; i++)
        {
            readByCoreProductIds = readByCoreProductIds + " ?, ";
        }

        readByCoreProductIds = readByCoreProductIds.substring(0, readByCoreProductIds.length() - 2) + " ) ";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read By Core Product IDs prepared Statement: {}", readByCoreProductIds);

        return readByCoreProductIds;
    }

    public String prepareAllPendingOrders()
    {
        String pending = this.buildEagerRead() + 
                " WHERE " + table() + "." + getFields().get(2) + " = 'PENDING' ";
        
        return pending;
    }

    public String prepareReadInTimeFrameByStatus()
    {
        String readInTimeFrame = buildEagerRead() + " WHERE ( " + table() + "."
                + getFields().get(1) + " BETWEEN ? AND ? ) AND ( " + table() +
                "." + getFields().get(2) + " = ? )";

        LOGGER.trace(this.getClass().getSimpleName());
        LOGGER.trace("Read In Time Frame prepared Statement: {}", readInTimeFrame);

        return readInTimeFrame;
    }

}
