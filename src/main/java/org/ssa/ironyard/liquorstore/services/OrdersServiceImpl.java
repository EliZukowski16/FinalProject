package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.model.Order;

@Service
public class OrdersServiceImpl implements OrdersService
{
    
    DAOOrder daoOrder;
    
    @Autowired
    public OrdersServiceImpl(DAOOrder daoOrder)
    {
        this.daoOrder = daoOrder;
    }

    @Override
    @Transactional
    public Order readOrder(Integer id)
    {
        return daoOrder.read(id);
    }

    @Override
    @Transactional
    public List<Order> readAllOrder()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<Order> readOrdersByCustomer(Integer customerID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<Order> readOrdersByProduct(Integer productID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public List<Order> readOrdersByCoreProdcut(Integer coreProductID)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public Order editOrder(Order order)
    {
        if(daoOrder.read(order.getId()) == null)
            return null;
        
        Order ord = new Order(order.getId(),order.getCustomer(),order.getDate(),order.getTotal(),order.getoD());
        return daoOrder.update(ord);
    }

    @Override
    @Transactional
    public Order addOrder(Order order)
    {
        Order ord = new Order(order.getId(),order.getCustomer(),order.getDate(),order.getTotal(),order.getoD());
        return daoOrder.insert(ord);
            
    }

    @Override
    @Transactional
    public boolean deleteOrder(Integer id)
    {
        if(daoOrder.read(id) == null)
            return false;
        
        return daoOrder.delete(id);
    }

}
