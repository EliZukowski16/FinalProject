package org.ssa.ironyard.liquorstore.dao;

import java.time.LocalDate;
import java.util.List;

import org.ssa.ironyard.liquorstore.model.Sales;

public interface DAOSales extends DAO<Sales>
{
    List<Sales> readSalesForPreviousDay();
    List<Sales> readSalesForPreviousDay(List<Integer> productIDs);
    List<Sales> readSalesForPreviousDay(Integer productID);

    List<Sales> readSalesForLast30Days();
    List<Sales> readSalesForLast30Days(List<Integer> productIDs);
    List<Sales> readSalesForLast30Days(Integer productID);
    
    List<Sales> readSalesForLast90Days();
    List<Sales> readSalesForLast90Days(List<Integer> productIDs);
    List<Sales> readSalesForLast90Days(Integer productID);
    
    List<Sales> readSalesForLast180Days();
    List<Sales> readSalesForLast180Days(List<Integer> productIDs);
    List<Sales> readSalesForLast180Days(Integer productID);
    
    List<Sales> readSalesForLastYear();
    List<Sales> readSalesForLastYear(List<Integer> productIDs);
    List<Sales> readSalesForLastYear(Integer productID);

    List<Sales> readSalesForProduct(List<Integer> productIDs);
    List<Sales> readSalesForProduct(Integer productID);
    List<Sales> readSalesForLastVariableDays(Integer numberOfDays, List<Integer> productIDs);
    List<Sales> readSalesInDateRange(LocalDate start, LocalDate end);
    List<Sales> readTopSellers(Integer numberOfDays, Integer numberOfProducts);

}
