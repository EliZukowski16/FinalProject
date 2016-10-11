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

        Customer customer = customerORM.map(results);

        List<OrderDetail> oD = new ArrayList<>();

        oD.add(this.mapOrderDetail(results));

        Order order = new Order(id, customer, date, total, oD, status);

        order.setLoaded(true);

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

        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Insert Details prepared Statement: {}", detailInsert);

        return detailInsert;
    }

    @Override
    public String prepareRead()
    {
        String read = " SELECT " + this.projection() + " , " + customerORM.projection() + " , "
                + coreProductORM.projection() + " , " + productORM.projection() + " , " + " order_detail.* " + 
                " FROM " + this.table() + 
                
                " " + this.customerJoin() + 
                " ON " + this.customerRelation() + " " + 
                
                " " + this.orderDetailJoin() + 
                " ON " + this.orderDetailRelation() +
                
                " " + this.productJoin() +
                " ON " + this.productRelation() + 
                
                " " + this.coreProductJoin() + 
                " ON " + this.coreProductRelation() +         
                
                " WHERE " + this.table() + "."
                + this.primaryKeys.get(0) + " = ? ";

        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Read prepared Statement: {}", read);

        return read;
    }

    @Override
    public String prepareReadAll()
    {
        String readAll = " SELECT " + this.projection() + " , " + customerORM.projection() + " , "
                + coreProductORM.projection() + " , " + productORM.projection() + " , " + " order_detail.* " + " FROM "
                + this.table() + this.customerJoin() + " ON " + this.customerRelation() + " " + this.coreProductJoin()
                + " ON " + this.coreProductRelation() + " " + this.productJoin() + " ON " + this.productRelation() + " "
                + this.orderDetailJoin() + " ON " + this.orderDetailRelation();

        LOGGER.debug(this.getClass().getSimpleName());
        LOGGER.debug("Read All prepared Statement: {}", readAll);

        return readAll;
    }

    private String customerJoin()
    {
        return " INNER JOIN " + customerORM.table();
    }

    private String customerRelation()
    {
        return this.table() + "." + this.foreignKeys.get(customerORM.table()) + " = " + customerORM.table() + "."
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
        return this.table() + "." + this.primaryKeys.get(0) + " = order_detail.order_id ";
    }

}
