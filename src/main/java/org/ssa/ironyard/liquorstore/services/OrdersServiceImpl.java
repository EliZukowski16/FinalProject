package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Product;

@Service
public class OrdersServiceImpl implements OrdersService
{

    DAOOrder daoOrder;
    DAOProduct daoProduct;

    @Autowired
    public OrdersServiceImpl(DAOOrder daoOrder, DAOProduct daoProduct)
    {
        this.daoOrder = daoOrder;
        this.daoProduct = daoProduct;
    }

    @Override
    @Transactional
    public Order readOrder(Integer id)
    {
        return daoOrder.read(id);
    }

    @Override
    @Transactional
    public List<Order> readAllOrder()
    {
        return daoOrder.readAll();
    }

    @Override
    @Transactional
    public List<Order> readOrdersByCustomer(Integer customerID)
    {
        List<Integer> ids = new ArrayList<>();

        if (customerID == null)
            return new ArrayList<>();

        ids.add(customerID);

        return daoOrder.readOrdersByCustomers(ids);

    }

    @Override
    @Transactional
    public List<Order> readOrdersByProduct(Integer productID)
    {
        List<Integer> ids = new ArrayList<>();

        if (productID == null)
            return new ArrayList<>();

        ids.add(productID);

        return daoOrder.readOrdersByProduct(ids);
    }

    @Override
    @Transactional
    public List<Order> readOrdersByCoreProdcut(Integer coreProductID)
    {
        List<Integer> ids = new ArrayList<>();

        if (coreProductID == null)
            return new ArrayList<>();

        ids.add(coreProductID);

        return daoOrder.readOrderByCoreProduct(ids);
    }

    @Override
    @Transactional
    public Order editOrder(Order order)
    {
        if (daoOrder.read(order.getId()) == null)
            return null;

        Order ord = new Order(order.getId(), order.getCustomer(), order.getDate(), order.getTotal(), order.getoD(),
                order.getOrderStatus());
        return daoOrder.update(ord);
    }

    @Override
    @Transactional
    public Order addOrder(Order order)
    {

        List<OrderDetail> odList = order.getoD();

        List<OrderDetail> outOfStock = new ArrayList();
        Order ordOutOfStock;

        for (int i = 0; i < odList.size(); i++)
        {
            Product p = daoProduct.read(odList.get(i).getProduct().getId());
            if (p.getInventory() <= 0)
            {
                OrderDetail od = new OrderDetail(p, null, null);
                outOfStock.add(od);
            }

        }

        if (outOfStock.size() > 0)
        {
            ordOutOfStock = new Order(null, null, null, null, outOfStock, null);
            return ordOutOfStock;
        }

        List<Integer> listProductIds = new ArrayList();

        for (int i = 0; i < order.getoD().size(); i++)
        {
            listProductIds.add(order.getoD().get(i).getProduct().getId());
        }

        List<Product> products = daoProduct.readByIds(listProductIds);

        List<OrderDetail> odPriceChange = new ArrayList();

        for (int i = 0; i < products.size(); i++)
        {
            for (int j = 0; j < order.getoD().size(); j++)
            {
                if (products.get(i).getId() == order.getoD().get(j).getProduct().getId())
                {
                    if (products.get(i).getPrice().compareTo(order.getoD().get(j).getProduct().getPrice()) != 0)
                    {
                        Product pPrice = products.get(i);
                        OrderDetail odPrice = new OrderDetail(pPrice, null, null);
                        odPriceChange.add(odPrice);
                    }
                }
            }

        }

        if (odPriceChange.size() > 0)
        {
            Order ordPriceChange = new Order(null, null, order.getDate(), null, odPriceChange, null);
            return ordPriceChange;
        }

        // get price
        Order ord = new Order(order.getCustomer(), order.getDate(), order.getTotal(), order.getoD(),
                order.getOrderStatus());

        return daoOrder.insert(ord);
    }

    @Override
    @Transactional
    public boolean deleteOrder(Integer id)
    {
        if (daoOrder.read(id) == null)
            return false;

        return daoOrder.delete(id);
    }

    @Override
    public List<Order> searchByCustomers(Customer customer)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> searchTimeFrame(LocalDate date1, LocalDate date2)
    {
        return daoOrder.readOrdersInTimeFrame(date1, date2);
    }

    @Override
    public List<Order> searchPast(LocalDate date1)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> serachFuture(LocalDate date1)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Order> searchMostRecent(LocalDate date1)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrderDetail> searchProduct(Product product)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<OrderDetail> searchTimeFrameAndProduct(LocalDate date1, LocalDate date2, Product product)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
