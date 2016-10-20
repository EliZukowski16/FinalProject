package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;
import org.ssa.ironyard.liquorstore.model.salesdata.TypeSalesData;

public interface SalesService
{
    boolean editSale(Order order);
    List<TypeSalesData> searchTimeFrame(LocalDate date1, LocalDate date2);
    List<Sales> searchType(CoreProduct.Type type);
    boolean searchKeyword(String keyword);
    Sales addSale(Sales sales);
    List<TypeSalesData> aggregateDailySales();
    Sales readSale(Integer id);
//    Map<Product, Map<LocalDate, Sales>> createDailySalesMap(List<SalesDaily> dailySales);
    List<TypeSalesData> readAllSales();
//    Map<Product, Map<Integer, Sales>> createWeeklySalesMap(List<SalesDaily> dailySales);
//    Map<Product, Map<Integer, Sales>> createMonthlySalesMap(List<SalesDaily> dailySales);
    List<Sales> searchProduct(Integer productID);
    List<Sales> searchProduct(List<Integer> productIDs);
    List<TypeSalesData> readSalesForYesterday();
    List<TypeSalesData> readSalesForYesterday(Integer productID);
    List<TypeSalesData> readSalesForYesterday(List<Integer> productIDs);
    List<TypeSalesData> readSalesForLast30Days();
    List<TypeSalesData> readSalesForLast30Days(Integer productID);
    List<TypeSalesData> readSalesForLast30Days(List<Integer> productIDs);
    List<TypeSalesData> readSalesForLast90Days();
    List<TypeSalesData> readSalesForLast90Days(Integer productID);
    List<TypeSalesData> readSalesForLast90Days(List<Integer> productIDs);
    List<TypeSalesData> readSalesForLast180Days();
    List<TypeSalesData> readSalesForLast180days(Integer productID);
    List<TypeSalesData> readSalesForLast180Days(List<Integer> productIDs);
    List<TypeSalesData> readSalesForLastYear();
    List<TypeSalesData> readSalesForLastYear(Integer productID);
    List<TypeSalesData> readSalesForLastYear(List<Integer> productIDs);
    List<TypeSalesData> readSalesForPastNumberOfDays(Integer numberOfDays);
    List<TypeSalesData> readSalesForPastNumberOfDays(Integer numberOfDays, List<Integer> productIDs);
    List<TypeSalesData> aggregateDailySales(LocalDate date);
}
