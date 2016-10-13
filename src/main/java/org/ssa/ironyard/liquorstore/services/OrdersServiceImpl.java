package org.ssa.ironyard.liquorstore.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.controller.CustomerController;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Product;

import com.mysql.cj.api.log.Log;

@Service
public class OrdersServiceImpl implements OrdersService
{

    DAOOrder daoOrder;
    DAOProduct daoProduct;

    
    static Logger LOGGER = LogManager.getLogger(OrdersServiceImpl.class);

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


        LOGGER.info("We Reacher order Service");
        
        LOGGER.info("Order from controller: {}", order);
        LOGGER.info("First Order Detail Price from controller: {}", order.getoD().get(0).getUnitPrice());
        LOGGER.info("Second Order Detail Price from controller: {}", order.getoD().get(1).getUnitPrice());
        
        
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

        
        LOGGER.info("made it past inventory check");
        LOGGER.info("Size of stock: {}",outOfStock.size());
        
        if(outOfStock.size() > 0)
        {
            LOGGER.info("Out of Stock", outOfStock);
            ordOutOfStock = new Order(null,null,null,null,outOfStock,null);


        }
        List<Integer> listProductIds = new ArrayList();

        for (int i = 0; i < order.getoD().size(); i++)
        {
            listProductIds.add(order.getoD().get(i).getProduct().getId());
        }

        
        LOGGER.info("made it past getting product ids");
        LOGGER.info("Size of product ID's: {}", listProductIds.size());
        
        List<Product> products = daoProduct.readByIds(listProductIds);
        
        LOGGER.info("size of product list from get product ids: {}", products.size());
        
        LOGGER.info("product list from get product ids: {}", products);



        List<OrderDetail> odPriceChange = new ArrayList();

        for (int i = 0; i < products.size(); i++)
        {
            for (int j = 0; j < order.getoD().size(); j++)
            {

                LOGGER.info("Product id from for loop: {}", products.get(i).getId());
                LOGGER.info("Product id from controller order: {}", order.getoD().get(j).getUnitPrice());
                
                    LOGGER.info("Product price from for loop: {}", products.get(i).getPrice());
                    LOGGER.info("Product price from controller order: {}", order.getoD().get(j).getUnitPrice());
                    if(products.get(i).getPrice().compareTo(order.getoD().get(j).getUnitPrice()) != 0)

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

        
        LOGGER.info("made it past price change check");
        LOGGER.info("Size of Price Change: {}", odPriceChange.size());
        
        if(odPriceChange.size() > 0)
        {
           LOGGER.info("Order Price has changed", odPriceChange);
           Order ordPriceChange = new Order(null,null,order.getDate(),null,odPriceChange,null);
           return ordPriceChange;
        }
        
        //get price
        Order ord = new Order(order.getCustomer(),order.getDate(),order.getTotal(),order.getoD(),order.getOrderStatus());
        
        LOGGER.info("order being sent cus ID: {}",ord.getCustomer().getId());
        LOGGER.info("order being sent date: {}",ord.getDate());
        LOGGER.info("order being sent total: {}",ord.getTotal());
        LOGGER.info("order being sent order Status: {}",ord.getOrderStatus());

        
        Order goodOrder = daoOrder.insert(ord);
        
        LOGGER.info("Good Order", goodOrder);
        
        return goodOrder;          
   


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
