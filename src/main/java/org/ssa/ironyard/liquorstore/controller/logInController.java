package org.ssa.ironyard.liquorstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@RestController
@RequestMapping("/TheBeerGuys")
public class logInController
{
    
    @RequestMapping("")
    public View home()
    {
        return new InternalResourceView("index.html");
    }
    
    @RequestMapping("/admin")
    public View admin()
    {
        return new InternalResourceView("adminIndex.html");
    }
    
    @RequestMapping("/customer")
    public View customer()
    {
        return new InternalResourceView("customerIndex.html");
    }
    
}
