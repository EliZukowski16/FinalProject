package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Product;

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
    List<Order> searchByCustomers(Customer customer);
    List<Order> searchTimeFrame(LocalDate date1, LocalDate date2);
    List<Order> searchPast(LocalDate date1);
    List<Order> searchFuture(LocalDate date1);
    List<Order> searchMostRecent(Integer numberOfOrders);
    List<OrderDetail> searchProduct(Product product);
    List<OrderDetail> searchTimeFrameAndProduct(LocalDate date1, LocalDate date2, Product product);
    List<Order> readUnfulfilledOrders();
    boolean approveOrder(Integer orderID);
    boolean rejectOrder(Integer orderID);
    Boolean changeOrderStatus(List<Order> ordersForStatusChange);
    
}
