package org.ssa.ironyard.liquorstore.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Customer;
import org.ssa.ironyard.liquorstore.model.Order;
import org.ssa.ironyard.liquorstore.model.Order.OrderStatus;
import org.ssa.ironyard.liquorstore.model.Product;
import org.ssa.ironyard.liquorstore.model.salesdata.ProductSalesData;
import org.ssa.ironyard.liquorstore.model.salesdata.ProductSalesData;
import org.ssa.ironyard.liquorstore.services.AdminServiceImpl;
import org.ssa.ironyard.liquorstore.services.AnalyticsServiceImpl;
import org.ssa.ironyard.liquorstore.services.CoreProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.CustomerServiceImpl;
import org.ssa.ironyard.liquorstore.services.OrdersServiceImpl;
import org.ssa.ironyard.liquorstore.services.ProductServiceImpl;
import org.ssa.ironyard.liquorstore.services.SalesServiceImpl;

@RestController
@RequestMapping("/TheBeerGuys/admin/{adminID}")
public class AdminController
{

    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    AnalyticsServiceImpl analyticsService;
    @Autowired
    CoreProductServiceImpl coreProductService;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    OrdersServiceImpl orderService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    SalesServiceImpl salesService;

    static Logger LOGGER = LogManager.getLogger(AdminController.class);

    public AdminController(AdminServiceImpl as, AnalyticsServiceImpl ans, CoreProductServiceImpl cps,
            CustomerServiceImpl cs, OrdersServiceImpl os, ProductServiceImpl ps, SalesServiceImpl ss)
    {
        adminService = as;
        analyticsService = ans;
        coreProductService = cps;
        customerService = cs;
        orderService = os;
        productService = ps;
        salesService = ss;

    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Customer>>> getAllCustomers()
    {
        LOGGER.info("Returning list of customers");
        Map<String, List<Customer>> response = new HashMap<>();
        List<Customer> customers = customerService.readAllCustomers();

        if (customers == null)
            response.put("error", customers);
        else
            response.put("success", customers);

        LOGGER.trace("This was a success", response);

        return ResponseEntity.ok().header("The Beer Guy Admin", "Customers").body(response);

    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Customer>> getCustomer(@PathVariable String id)
    {
        LOGGER.info("returning single customer");
        Map<String, Customer> response = new HashMap<>();
        Customer customer = customerService.readCustomer(Integer.parseInt(id));

        if (customer == null)
            response.put("error", customer);
        else
        {
            LOGGER.info("Found customer", customer.getId(), customer.getUserName(), customer.getPassword(),
                    customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getBirthDate());
            response.put("success", customer);
        }

        return ResponseEntity.ok().header("Customers", "Customer").body(response);

    }

    @RequestMapping(value = "/customers/{id}/orders", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Order>>> getCustomerOrders(@PathVariable String id)
    {
        Map<String, List<Order>> response = new HashMap<>();

        Integer customerID = Integer.parseInt(id);

        List<Order> orders = new ArrayList<>();

        orders = orderService.readOrdersByCustomer(customerID);

        if (orders.isEmpty())
            response.put("error", new ArrayList<>());
        else
            response.put("success", orders);

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    public boolean deleteCustomer(@PathVariable String id)
    {
        LOGGER.info("deleting single customer");

        Integer cusID = Integer.parseInt(id);

        boolean deleteSuccess = customerService.deleteCustomer(cusID);
        LOGGER.info("Delete was successful ", deleteSuccess);

        return deleteSuccess;

    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Product>>> getProducts()
    {
        Map<String, List<Product>> response = new HashMap<>();
        List<Product> productsList = productService.readAllProducts();

        LOGGER.info("getttinga all products");

        if (productsList == null)
            response.put("error", productsList);
        else
            response.put("success", productsList);

        LOGGER.trace("checking for a success", response);

        return ResponseEntity.ok().header("The Beer Guys Admin", "Products").body(response);

    }

    @RequestMapping(value = "/inventory/lowInventory", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Product>>> getLowInventory()
    {
        List<Product> lowInventory = productService.readLowInventory();

        Map<String, List<Product>> response = new HashMap<>();

        if (lowInventory.isEmpty())
            response.put("error", new ArrayList<>());
        else
            response.put("success", lowInventory);

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, Product>> getProdcut(@PathVariable String id)
    {
        Map<String, Product> response = new HashMap<>();
        LOGGER.info("getting one product");

        Integer prodID = Integer.parseInt(id);
        Product prod = productService.readProduct(prodID);

        if (prod == null)
            response.put("error", prod);
        else
            response.put("success", prod);

        LOGGER.trace("checking for a success", response);

        return ResponseEntity.ok().header("Products", "Product Detail").body(response);
    }

    @RequestMapping(value = "/products/{id}/orders", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Order>>> getProductOrders(@PathVariable String id)
    {
        return null;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Product>> addProduct(HttpServletRequest request)
    {
        return null;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Product>> editProduct(@PathVariable String id, HttpServletRequest request)
    {
        return null;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Order>>> getAllOrders()
    {
        Map<String, List<Order>> response = new HashMap<>();

        List<Order> orders = orderService.readAllOrder();

        if (orders.isEmpty())
            response.put("error", new ArrayList<>());
        else
            response.put("success", orders);

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/orders/pending", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, List<Order>>> getPendingOrders()
    {
        Map<String, List<Order>> response = new HashMap<>();

        List<Order> orders = orderService.readPendingOrders();

        if (orders.isEmpty())
            response.put("error", new ArrayList<>());
        else
            response.put("success", orders);

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/orders/pending", method = RequestMethod.POST)
    public Boolean changeOrderStatus(@RequestBody List<Map<String, Object>> body)
    {
        LOGGER.info("We made it to the pending cont" + body);
        List<Order> ordersForStatusChange = new ArrayList<>();

        for (Map<String, Object> map : body)
        {
            for (Entry<String, Object> e : map.entrySet())
            {
                Integer orderID = Integer.parseInt((String) e.getKey());
                OrderStatus status = OrderStatus.getInstance((String) e.getValue());

                LOGGER.info("order ID: {}", orderID);
                LOGGER.info("orderStatus: {}", status);

                ordersForStatusChange.add(new Order.Builder().id(orderID).orderStatus(status).build());
            }
        }

        return orderService.changeOrderStatus(ordersForStatusChange);

    }

    @RequestMapping(value = "/orders/pending/{id}/approve", method = RequestMethod.POST)
    public Boolean approveOrder(@PathVariable String id)
    {
        Integer orderID = Integer.parseInt(id);

        return orderService.approveOrder(orderID);

    }

    @RequestMapping(value = "/orders/pending/{id}/reject", method = RequestMethod.POST)
    public Boolean rejectOrder(@PathVariable String id)
    {
        Integer orderID = Integer.parseInt(id);

        return orderService.rejectOrder(orderID);
    }

    @RequestMapping(value = "/orders/unfulfilled", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<Order>>> getFutureOrders()
    {
        LOGGER.info("made it to future orders");
        Map<String, List<Order>> response = new HashMap<>();
        List<Order> orderList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LOGGER.info("Today: {}", today);

        orderList = orderService.readFutureApprovedOrders(today);

        LOGGER.info(orderList.size());

        if (orderList.size() == 0)
            response.put("error", orderList);
        else
            response.put("success", orderList);

        return ResponseEntity.ok().header("Admin Orders", "Unfulfilled Orders").body(response);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<Product>>> searchKeywordType(@PathVariable String adminID,
            HttpServletRequest request)
    {
        Map<String, List<Product>> response = new HashMap<>();

        LOGGER.info("Going to the search");

        LOGGER.info(request.getParameterValues("types") + " request types");
        LOGGER.info(request.getParameter("keywords") + " request keywords");

        String keyword = request.getParameter("keywords");
        LOGGER.info(keyword + " String keyword");
        String[] tagArray = keyword.split("\\s");
        String[] typeArray = request.getParameterValues("types");

        for (int i = 0; i < tagArray.length; i++)
        {
            LOGGER.info(tagArray[i] + " String array tags" + i);
        }

        for (int i = 0; i < typeArray.length; i++)
        {
            LOGGER.info(typeArray[i] + " String array type" + i);
        }

        List<Tag> tags = new ArrayList<>();
        List<Type> types = new ArrayList<>();

        tags = Stream.of(tagArray).map(Tag::new).collect(Collectors.toList());
        LOGGER.info("hello");
        types = Stream.of(typeArray).map(Type::getInstance).collect(Collectors.toList());

        LOGGER.info(tags + "List tags");
        LOGGER.info(types + "List Tyeps");

        List<Product> products = productService.searchProduct(tags, types);

        LOGGER.info(products + "products");

        if (products.size() == 0)
        {
            response.put("error", products);
        }
        else
        {
            response.put("success", products);
        }

        return ResponseEntity.ok().header("Products", "Search By Keyword").body(response);
    }

    @RequestMapping(value = "/inventory/sales", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<ProductSalesData>>> getAllDailySales()
    {
        Map<String, List<ProductSalesData>> response = new HashMap<>();

        List<ProductSalesData> salesData = salesService.readAllSales();

        if (!salesData.isEmpty())
            response.put("success", salesData);
        else
            response.put("error", new ArrayList<>());

        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public Boolean addStock(@RequestBody List<Map<Integer, Integer>> body)
    {
        Map<Integer, Integer> productStockOrders = new HashMap<>();

        LOGGER.info("We made it to add Inv: {}", body);

        if (body.isEmpty())
            return false;

        for (Map<Integer, Integer> p : body)
        {
            for (Entry<Integer, Integer> e : p.entrySet())
            {
                productStockOrders.put(e.getKey(), e.getValue());

            }
        }

        if (!productService.addStock(productStockOrders).isEmpty())
            return true;
        return false;
    }

    @RequestMapping(value = "/TopSellers", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<ProductSalesData>>> getTopSellers()
    {
        Map<String, List<ProductSalesData>> response = new HashMap<>();

        List<ProductSalesData> topSellers = salesService.readTopSellersForLast30Days(50);

        if (!topSellers.isEmpty())
            response.put("success", topSellers);
        else
            response.put("error", new ArrayList<>());

        return ResponseEntity.ok().body(response);
    }

}
