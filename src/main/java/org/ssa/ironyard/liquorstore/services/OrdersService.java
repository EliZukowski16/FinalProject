package org.ssa.ironyard.liquorstore.services;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.Order;

public interface OrdersService
{
    Order readOrder(Integer id);
    List<Order> readAllOrder();
    List<Order> readOrdersByCustomer(Integer customerID);
    List<Order> readOrdersByProduct(Integer productID);
    List<Order> readOrdersByCoreProdcut(Integer coreProductID);
    Order editOrder(Order order);
    Order addOrder(Order order);
    boolean deleteOrder(Integer id);
}
