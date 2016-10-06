package org.ssa.ironyard.liquorstore.dao.orm;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Product;

public class ORMOrderImpl extends AbstractORM<Order> implements ORM<Order>
{
    static Logger LOGGER = LogManager.getLogger(ORMOrderImpl.class);
    
    AbstractORM<Customer> customerORM;
    AbstractORM<Product> productORM;
    
    public ORMOrderImpl()
    {
        this.primaryKeys.add("id");
        
        this.fields.add("customer_id");
        this.fields.add("date");
        this.fields.add("total");
        
        this.foreignKeys.put("customer", "customer_id"); 
        
        customerORM = new ORMCustomerImpl();
        productORM = new ORMProductImpl();
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
        
        Customer customer = customerORM.map(results);
        
        List<OrderDetail> oD = new ArrayList<>();  
        
        Order order = new Order(id, customer, date, total, oD);
        
        order.setLoaded(true);
        
        return order;
    }
    
    

    public String prepareInsertDetail()
    {
        String detailInsert = " INSERT INTO order_detail (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        
        LOGGER.debug(detailInsert);
        
        return  detailInsert;
    }
    
    @Override
    public String prepareRead()
    {
        String read = " SELECT " + this.projection() + " , " + customerORM.projection() + " FROM " + this.table() + this.customerJoin() +
                " ON " + this.customerRelation() + " WHERE " + this.table() + "." + this.primaryKeys.get(0) + " = ? ";
        
        LOGGER.debug(read);
        
        return read;
    }
    
    private String customerJoin()
    {
        return " JOIN " + customerORM.table();
    }
    
    private String customerRelation()
    {
        return this.table() + "." + this.foreignKeys.get(customerORM.table()) + " = " + customerORM.table() + "." + customerORM.getPrimaryKeys().get(0);
    }
    
    private String orderDetailJoin()
    {
        return " JOIN order_detail ";
    }

}
