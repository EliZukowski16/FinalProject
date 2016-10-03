package org.ssa.ironyard.liquorstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssa.ironyard.liquorstore.services.AdminService;
import org.ssa.ironyard.liquorstore.services.AnalyticsService;
import org.ssa.ironyard.liquorstore.services.CoreProductService;
import org.ssa.ironyard.liquorstore.services.CustomerService;
import org.ssa.ironyard.liquorstore.services.OrdersService;
import org.ssa.ironyard.liquorstore.services.ProductService;
import org.ssa.ironyard.liquorstore.services.SalesService;

@RestController
@RequestMapping("/TheBeerGuys")
public class AdminController
{
    
    @Autowired
    AdminService adminService;
    @Autowired
    AnalyticsService analyticsService;
    @Autowired
    CoreProductService coreProductService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrdersService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    SalesService salesService;
    
    public AdminController(AdminService as, AnalyticsService ans, CoreProductService cps, CustomerService cs, OrdersService os, ProductService ps, SalesService ss)
    {
        adminService = as;
        analyticsService = ans;
        coreProductService = cps;
        customerService = cs;
        orderService = os;
        productService = ps;
        salesService = ss;       
        
    }
}
