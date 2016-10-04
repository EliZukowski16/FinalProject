package org.ssa.ironyard.liquorstore.dao.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Product;

public class ORMOrderImpl extends AbstractORM<Order> implements ORM<Order>
{
    AbstractORM<Customer> customerORM;
    AbstractORM<Product> productORM;
    
    public ORMOrderImpl()
    {
        this.primaryKeys.add("id");
        
        this.fields.add("customerID");
        this.fields.add("date");
        this.fields.add("total");
        
        this.foreignKeys.put("customer", "customerID"); 
        
        customerORM = new ORMCustomerImpl();
        productORM = new ORMProductImpl();
    }

    @Override
    public String table()
    {
        return "order";
    }

    @Override
    public Order map(ResultSet results) throws SQLException
    {
        Integer id = results.getInt("order.id");
        Float total = results.getFloat("order.total");
        LocalDateTime date = results.getTimestamp("order.date").toLocalDateTime();
        
        Customer customer = customerORM.map(results);
        
        List<OrderDetail> oD = new ArrayList<>();        
        return new Order(id, customer, date, total, oD);
    }

}
