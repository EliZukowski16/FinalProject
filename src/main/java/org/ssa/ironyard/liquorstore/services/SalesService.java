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
    List<SalesDaily> readAllSales();
    Map<Product, Map<Integer, Sales>> createWeeklySalesMap(List<SalesDaily> dailySales);
    Map<Product, Map<Integer, Sales>> createMonthlySalesMap(List<SalesDaily> dailySales);
    List<Sales> searchProduct(Integer productID);
    List<Sales> searchProduct(List<Integer> productIDs);
    List<Sales> readSalesForYesterday();
    List<Sales> readSalesForYesterday(Integer productID);
    List<Sales> readSalesForYesterday(List<Integer> productIDs);
    List<Sales> readSalesForLast30Days();
    List<Sales> readSalesForLast30Days(Integer productID);
    List<Sales> readSalesForLast30Days(List<Integer> productIDs);
    List<Sales> readSalesForLast90Days();
    List<Sales> readSalesForLast90Days(Integer productID);
    List<Sales> readSalesForLast90Days(List<Integer> productIDs);
    List<Sales> readSalesForLast180Days();
    List<Sales> readSalesForLast180days(Integer productID);
    List<Sales> readSalesForLast180Days(List<Integer> productIDs);
    List<Sales> readSalesForLastYear();
    List<Sales> readSalesForLastYear(Integer productID);
    List<Sales> readSalesForLastYear(List<Integer> productIDs);
    List<Sales> readSalesForPastNumberOfDays(Integer numberOfDays);
    List<Sales> readSalesForPastNumberOfDays(Integer numberOfDays, List<Integer> productIDs);
    List<Sales> aggregateDailySales(LocalDate date);
}
