package org.ssa.ironyard.liquorstore.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssa.ironyard.liquorstore.dao.DAOOrder;
import org.ssa.ironyard.liquorstore.dao.DAOProduct;
import org.ssa.ironyard.liquorstore.dao.DAOSales;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderDetail;
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.Sales;
import org.ssa.ironyard.liquorstore.model.SalesDaily;
import org.ssa.ironyard.liquorstore.model.SalesDaily.Builder;

@Service
public class OrdersServiceImpl implements OrdersService
{

    DAOOrder daoOrder;
    DAOProduct daoProduct;
    DAOSales daoSales;

    static Logger LOGGER = LogManager.getLogger(OrdersServiceImpl.class);

    @Autowired
    public OrdersServiceImpl(DAOOrder daoOrder, DAOProduct daoProduct, DAOSales daoSales)
    {
        this.daoOrder = daoOrder;
        this.daoProduct = daoProduct;
        this.daoSales = daoSales;
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

        if (customerID == null)
            return new ArrayList<>();

        List<Order> customerOrders = daoOrder.readOrdersByCustomer(customerID);

        customerOrders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return customerOrders;

    }

    @Override
    @Transactional
    public List<Order> readOrdersByProduct(Integer productID)
    {
        if (productID == null)
            return new ArrayList<>();

        List<Order> productOrders = daoOrder.readOrdersByProduct(productID);

        productOrders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return productOrders;
    }

    @Override
    @Transactional
    public List<Order> readOrdersByCoreProdcut(Integer coreProductID)
    {
        if (coreProductID == null)
            return new ArrayList<>();

        List<Order> coreProductOrders = daoOrder.readOrdersByCoreProduct(coreProductID);

        coreProductOrders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return coreProductOrders;
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

        List<OrderDetail> odList = order.getoD();

        List<OrderDetail> outOfStock = new ArrayList();
        Order ordOutOfStock;

        for (int i = 0; i < odList.size(); i++)
        {
            Product p = daoProduct.read(odList.get(i).getProduct().getId());
            if (p.getInventory() <= 0)// add checking against how many they
                                      // ordered
            {
                OrderDetail od = new OrderDetail(p, null, null);
                outOfStock.add(od);
            }

        }

        LOGGER.info("made it past inventory check");
        LOGGER.info("Size of stock: {}", outOfStock.size());

        if (outOfStock.size() > 0)
        {
            LOGGER.info("Out of Stock", outOfStock);
            ordOutOfStock = new Order(null, null, null, null, outOfStock, null);

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
                LOGGER.info("Product id from controller order: {}", order.getoD().get(j).getProduct().getId());

                if (products.get(i).getId() == order.getoD().get(j).getProduct().getId())
                {
                    LOGGER.info("Product price from for loop: {}", products.get(i).getPrice());
                    LOGGER.info("Product price from controller order: {}", order.getoD().get(j).getUnitPrice());
                    if (products.get(i).getPrice().compareTo(order.getoD().get(j).getUnitPrice()) != 0)
                    {
                        LOGGER.info("price is not equal");
                        Product pPrice = products.get(i);
                        OrderDetail odPrice = new OrderDetail(pPrice, null, null);
                        odPriceChange.add(odPrice);
                    }
                }
            }

        }

        LOGGER.info("made it past price change check");
        LOGGER.info("Size of Price Change: {}", odPriceChange.size());

        if (odPriceChange.size() > 0)
        {
            LOGGER.info("Order Price has changed", odPriceChange);
            Order ordPriceChange = new Order(null, null, order.getDate(), null, odPriceChange, null);
            return ordPriceChange;
        }

        // get price
        Order ord = new Order(order.getCustomer(), order.getDate(), order.getTotal(), order.getoD(),
                order.getOrderStatus());

        LOGGER.info("order being sent cus ID: {}", ord.getCustomer().getId());
        LOGGER.info("order being sent date: {}", ord.getDate());
        LOGGER.info("order being sent total: {}", ord.getTotal());
        LOGGER.info("order being sent order Status: {}", ord.getOrderStatus());

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
        List<Order> orders = daoOrder.readOrdersInTimeFrame(date1, date2);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }

    @Override
    public List<Order> searchPast(LocalDate date1)
    {
        List<Order> orders = daoOrder.readOrdersInThePast(date1);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }

    @Override
    public List<Order> searchFuture(LocalDate date1)
    {
        List<Order> orders = daoOrder.readOrdersInTheFuture(date1);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;

    }

    @Override
    public List<Order> searchMostRecent(Integer numberOfOrders)
    {
        List<Order> orders = daoOrder.readMostRecentOrders(numberOfOrders);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
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

    @Override
    public List<Order> readPendingOrders()
    {
        List<Order> pendingOrders = new ArrayList<>();

        pendingOrders = daoOrder.readPendingOrders(50);

        pendingOrders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        return pendingOrders;
    }
    
    @Override
    public List<Order> readFutureApprovedOrders(LocalDate start)
    {
        List<Order> orders = daoOrder.readOrdersInTheFutureByStatus(OrderStatus.APPROVED, start);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }
    
    @Override
    public List<Order> readPastApprovedOrders(LocalDate end)
    {
        List<Order> orders = daoOrder.readOrdersInThePastByStatus(OrderStatus.APPROVED, end);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }
    
    @Override
    public List<Order> readApprovedOrdersInTimeFrame(LocalDate start, LocalDate end)
    {
        List<Order> orders = daoOrder.readOrdersInTimeFrameByStatus(OrderStatus.APPROVED, start, end);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }
    
    @Override
    public List<Order> readFutureRejectedOrders(LocalDate start)
    {
        List<Order> orders = daoOrder.readOrdersInTheFutureByStatus(OrderStatus.REJECTED, start);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }
    
    @Override
    public List<Order> readPastRejectedOrders(LocalDate end)
    {
        List<Order> orders = daoOrder.readOrdersInThePastByStatus(OrderStatus.REJECTED, end);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }
    
    @Override
    public List<Order> readRejectedOrdersInTimeFrame(LocalDate start, LocalDate end)
    {
        List<Order> orders = daoOrder.readOrdersInTimeFrameByStatus(OrderStatus.REJECTED, start, end);

        orders.sort((o1, o2) -> o2.getTimeOfOrder().compareTo(o1.getTimeOfOrder()));

        return orders;
    }

    @Override
    @Transactional
    public boolean approveOrder(Integer orderID)
    {
        if (orderID == null)
            return false;

        Order order = daoOrder.read(orderID);
        Order updatedOrder;
        LOGGER.info("Got order with ID : {}", order.getId());

        if (!order.getOrderStatus().equals(OrderStatus.PENDING) || (order == null))
            return false;

        for (OrderDetail detail : order.getoD())
        {
            LOGGER.info("Product in Order Detail : {}", detail.getProduct().getId());

            Integer updatedInventory = detail.getProduct().getInventory() - detail.getQty();

            LOGGER.info("Updated Inventory : {}", updatedInventory);

            if (daoProduct.update(detail.getProduct().of().inventory(updatedInventory).build()) == null)
                throw new RuntimeException(
                        "Product : " + detail.getProduct().getId() + "Inventory could not be updated");
        }

        if ((updatedOrder = daoOrder.update(order.of().orderStatus(OrderStatus.APPROVED).build())) == null)
            throw new RuntimeException("Order : " + order.getId() + " could not be approved");
        
        for(OrderDetail detail : updatedOrder.getoD())
        {
            Sales sales = ((Builder) new SalesDaily.Builder()
                    .product(detail.getProduct()))
                    .dateSold(LocalDate.now())
                    .numberSold(detail.getQty())
                    .totalValue(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQty())))
                    .aggregateSales(false)
                    .build();
            
            if(daoSales.insert(sales) == null)
                throw new RuntimeException("Sales date for " + sales.getProduct().getCoreProduct().getName() + " could not be entered");
        }

        return true;

    }

    @Override
    @Transactional
    public boolean rejectOrder(Integer orderID)
    {
        if (orderID == null)
            return false;

        Order order = daoOrder.read(orderID);

        if (!order.getOrderStatus().equals(OrderStatus.PENDING) || (order == null))
            return false;

        if (daoOrder.update(order.of().orderStatus(OrderStatus.REJECTED).build()) == null)
            throw new RuntimeException("Order : " + order.getId() + " could not be rejected");

        return true;

    }

    @Override
    @Transactional
    public Boolean changeOrderStatus(List<Order> ordersForStatusChange)
    {
        if (ordersForStatusChange.isEmpty())
            return false;

        for (Order o : ordersForStatusChange)
        {
            switch (o.getOrderStatus())
            {
            case APPROVED:
                if (!approveOrder(o.getId()))
                    throw new RuntimeException("Order " + o.getId() + " could not be approved");
                break;
            case REJECTED:
                if (!rejectOrder(o.getId()))
                    throw new RuntimeException("Order " + o.getId() + " could not be rejected");
                break;
            default:
                throw new RuntimeException("Order " + o.getId() + " with Order Status " + o.getOrderStatus().name() + " not recognized");
            }
        }
        
        return true;
        
    }

}
