package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;

public interface SalesService
{
    boolean editSale(Order order);
    List<Sales> searchTimeFrame(LocalDate date1, LocalDate date2);
    List<Sales> searchType(CoreProduct.Type type);
    boolean searchKeyword(String keyword);
    Sales addSale(Sales sales);
    List<Sales> aggregateDailySales();
    Sales readSale(Integer id);
    Map<Product, Map<LocalDate, Sales>> createDailySalesMap(List<SalesDaily> dailySales);
    List<Sales> readAllSales();
    Map<Product, Map<Integer, Sales>> createWeeklySalesMap(List<SalesDaily> dailySales);
    Map<Product, Map<Integer, Sales>> createMonthlySalesMap(List<SalesDaily> dailySales);
    List<Sales> searchProduct(Integer productID);
    List<Sales> searchProduct(List<Integer> productIDs);
    List<Sales> readSalesForYesterday();
    List<Sales> readSalesForYesterday(Integer productID);
    List<Sales> readSalesForYesterday(List<Integer> productIDs);
}
