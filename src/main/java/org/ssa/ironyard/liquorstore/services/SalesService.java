package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Order;

public interface SalesService
{
    boolean addSale(Order order);
    boolean editSale(Order order);
    boolean readSale();
    boolean readAllSales();
    boolean searchTimeFrame(LocalDate date1, LocalDate date2);
    boolean searchType(CoreProduct.Type type);
    boolean searchKeyword(String keyword);
}
