package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.ssa.ironyard.liquorstore.model.CoreProduct;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;
import org.ssa.ironyard.liquorstore.model.salesdata.ProductSalesData;
import org.ssa.ironyard.liquorstore.model.salesdata.TypeSalesData;

public interface SalesService
{
    boolean editSale(Order order);
    List<ProductSalesData> searchTimeFrame(LocalDate date1, LocalDate date2);
    List<Sales> searchType(CoreProduct.Type type);
    boolean searchKeyword(String keyword);
    Sales addSale(Sales sales);
    List<ProductSalesData> aggregateDailySales();
    Sales readSale(Integer id);
//    Map<Product, Map<LocalDate, Sales>> createDailySalesMap(List<SalesDaily> dailySales);
    List<ProductSalesData> readAllSales();
//    Map<Product, Map<Integer, Sales>> createWeeklySalesMap(List<SalesDaily> dailySales);
//    Map<Product, Map<Integer, Sales>> createMonthlySalesMap(List<SalesDaily> dailySales);
    List<ProductSalesData> searchProduct(Integer productID);
    List<ProductSalesData> searchProduct(List<Integer> productIDs);
    List<ProductSalesData> readSalesForYesterday();
    List<ProductSalesData> readSalesForYesterday(Integer productID);
    List<ProductSalesData> readSalesForYesterday(List<Integer> productIDs);
    List<ProductSalesData> readSalesForLast30Days();
    List<ProductSalesData> readSalesForLast30Days(Integer productID);
    List<ProductSalesData> readSalesForLast30Days(List<Integer> productIDs);
    List<ProductSalesData> readSalesForLast90Days();
    List<ProductSalesData> readSalesForLast90Days(Integer productID);
    List<ProductSalesData> readSalesForLast90Days(List<Integer> productIDs);
    List<ProductSalesData> readSalesForLast180Days();
    List<ProductSalesData> readSalesForLast180days(Integer productID);
    List<ProductSalesData> readSalesForLast180Days(List<Integer> productIDs);
    List<ProductSalesData> readSalesForLastYear();
    List<ProductSalesData> readSalesForLastYear(Integer productID);
    List<ProductSalesData> readSalesForLastYear(List<Integer> productIDs);
    List<ProductSalesData> readSalesForPastNumberOfDays(Integer numberOfDays);
    List<ProductSalesData> readSalesForPastNumberOfDays(Integer numberOfDays, List<Integer> productIDs);
    List<ProductSalesData> aggregateDailySales(LocalDate date);
    List<ProductSalesData> readTopSellersForLast30Days(Integer numberOfProducts);
}
