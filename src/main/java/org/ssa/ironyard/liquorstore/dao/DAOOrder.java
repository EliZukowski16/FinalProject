package org.ssa.ironyard.liquorstore.dao;

import java.time.LocalDate;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.Order;

public interface DAOOrder extends DAO<Order>
{

    public Order insertDetail(Order domain);
    public List<Order> readOrdersByCustomers(List<Integer> customerIDs);
    public List<Order> readOrdersByProduct(List<Integer> productIDs);
    public List<Order> readOrderByCoreProduct(List<Integer> coreProductIDs);
    public List<Order> readOrdersInTimeFrame(LocalDate start, LocalDate end);
    public List<Order> readOrdersInThePast(LocalDate end);
    public List<Order> readOrdersInTheFuture(LocalDate start);
    public List<Order> readMostRecentOrders(Integer numberOfOrders);

}
