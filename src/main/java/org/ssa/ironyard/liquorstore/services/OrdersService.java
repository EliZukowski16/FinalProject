package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.stereotype.Component;
import org.ssa.ironyard.liquorstore.model.Order;

@Component
public class OrdersService implements OrdersServiceInt
{

    @Override
    public Order readOrder(Integer id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> readAllOrder()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> readOrdersByCustomer(Integer customerID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> readOrdersByProduct(Integer productID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> readOrdersByCoreProdcut(Integer coreProductID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order editOrder(Order order)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order addOrder(Order order)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Order deleteOrder(Order order)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
