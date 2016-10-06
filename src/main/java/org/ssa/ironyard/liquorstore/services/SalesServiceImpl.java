package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Order;

@Service
public class SalesServiceImpl implements SalesService
{

    @Override
    public boolean addSale(Order order)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean editSale(Order order)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean readSale()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean readAllSales()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchTimeFrame(LocalDate date1, LocalDate date2)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchType(Type type)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean searchKeyword(String keyword)
    {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
