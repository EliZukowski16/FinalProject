package org.ssa.ironyard.liquorstore.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.ssa.ironyard.liquorstore.dao.orm.ORM;
import org.ssa.ironyard.liquorstore.dao.orm.ORMCoreProductImpl;
import org.ssa.ironyard.liquorstore.dao.orm.ORMOrderImpl;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;

import com.mysql.cj.api.jdbc.Statement;

public class DAOOrderImpl extends AbstractDAOOrder implements DAOOrder
{

    protected DAOOrderImpl(DataSource dataSource)
    {
        super(new ORMOrderImpl(), dataSource);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Order domainToInsert) throws SQLException
    {
        insertStatement.setInt(1, domainToInsert.getCustomerID().getId());
        insertStatement.setTimestamp(2, Timestamp.valueOf(domainToInsert.getDate()));
        insertStatement.setFloat(3, domainToInsert.getTotal());
    }
    
    @Override
    public Order insert(Order domain)
    {
        Order order;
        
        
        
        if((order = super.insert(domain)) != null)
        {
            List<OrderDetail> orderDetails = new ArrayList<>();
            
            for(OrderDetail od : orderDetails)
            {
            
            KeyHolder generatedId = new GeneratedKeyHolder();
            if(this.springTemplate.update((Connection conn) ->
            {
                PreparedStatement statement = conn.prepareStatement(
                        ((ORMOrderImpl) this.orm).prepareInsertDetail(), Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, coreProduct.getId());
                statement.setString(2, t.getName());
                return statement;
            }, generatedId) == 1)
        }
        }
        
        
        
        // TODO Auto-generated method stub
        return super.insert(domain);
    }

    @Override
    protected Order afterInsert(Order copy, Integer id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Order afterUpdate(Order copy)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Order domainToUpdate)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
