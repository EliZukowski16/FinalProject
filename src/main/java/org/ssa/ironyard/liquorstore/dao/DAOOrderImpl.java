package org.ssa.ironyard.liquorstore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        insertStatement.setInt(1, domainToInsert.getCustomer().getId());
        insertStatement.setTimestamp(2, Timestamp.valueOf(domainToInsert.getDate()));
        insertStatement.setFloat(3, domainToInsert.getTotal());
    }

    @Override
    public Order insertDetail(Order domain)
    {
        if (domain.getId() != null)
        {
            List<OrderDetail> orderDetails = new ArrayList<>();

            for (OrderDetail od : domain.getoD())
            {

                KeyHolder generatedId = new GeneratedKeyHolder();
                if (this.springTemplate.update((Connection conn) ->
                {
                    PreparedStatement statement = conn.prepareStatement(((ORMOrderImpl) this.orm).prepareInsertDetail(),
                            Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, domain.getId());
                    statement.setInt(2, od.getProduct().getId());
                    statement.setInt(3, od.getQty());
                    statement.setFloat(4, od.getUnitPrice());
                    return statement;
                }, generatedId) == 1)
                {
                    orderDetails
                            .add(new OrderDetail(domain.getId(), od.getProduct(), od.getQty(), od.getUnitPrice()));
                }
            }

            Order copy = (Order) domain.clone();

            copy.setoD(orderDetails);

            return copy;
        }

        return null;
    }

    @Override
    public Order insert(Order domain)
    {
        Order order;

        if ((order = super.insert(domain)) != null)
        {
            return this.insertDetail(order);
        }

        return null;
    }

    @Override
    protected Order afterInsert(Order copy, Integer id)
    {
        Order order;
        order = new Order(id, copy.getCustomer(), copy.getDate(), copy.getTotal(), copy.getoD());
        order.setLoaded(true);

        return order;
    }

    @Override
    protected Order afterUpdate(Order copy)
    {
        copy.setLoaded(true);

        return copy;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Order domainToUpdate)
    {
        return new PreparedStatementSetter()
        {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException
            {
                ps.setInt(1, domainToUpdate.getCustomer().getId());
                ps.setTimestamp(2, Timestamp.valueOf(domainToUpdate.getDate()));
                ps.setFloat(3, domainToUpdate.getTotal());
            }

        };

    }

}
