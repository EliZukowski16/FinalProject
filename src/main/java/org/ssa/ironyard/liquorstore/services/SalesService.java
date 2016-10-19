package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Sales;

public interface SalesService
{
    boolean editSale(Order order);
    List<Sales> readAllSales();
    boolean searchTimeFrame(LocalDate date1, LocalDate date2);
    boolean searchType(CoreProduct.Type type);
    boolean searchKeyword(String keyword);
    Sales addSale(Sales sales);
    List<Sales> aggregateDailySales();
    Sales readSale(Integer id);
}
