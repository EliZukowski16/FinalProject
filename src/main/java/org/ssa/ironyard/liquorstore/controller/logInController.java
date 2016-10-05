package org.ssa.ironyard.liquorstore.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.ssa.ironyard.liquorstore.model.User;
import org.ssa.ironyard.liquorstore.services.LogInService;

@RestController
@RequestMapping("/TheBeerGuys")
public class logInController
{
    
    @Autowired
    LogInService logInService;
    
    @RequestMapping("")
    public View home()
    {
        return new InternalResourceView("index.html");
    }
    
    @RequestMapping(value = "/login/{userName}/{password}", method = RequestMethod.POST)
    public ResponseEntity<Map<String,User>> Authenticate(@PathVariable String userName, @PathVariable String password)
    {
        Map<String,User> response = new HashMap<>();
        User u = logInService.checkAuthentication(userName, password);
        
        if(u == null)
        {
            response.put("error", u);
        }
        else
        {
            response.put("success", u);
        }
        
        return ResponseEntity.ok().header("log in", "Check").body(response);
    }
    
    @RequestMapping(value = "/admin")
    public View admin()
    {
        return new InternalResourceView("adminIndex.html");
    }
    
    @RequestMapping(value = "/customer")
    public View customer()
    {
        return new InternalResourceView("customerIndex.html");
    }
    
}
