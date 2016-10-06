package org.ssa.ironyard.liquorstore.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.ssa.ironyard.liquorstore.model.User;
import org.ssa.ironyard.liquorstore.services.LogInServiceImpl;

@RestController
@RequestMapping("/TheBeerGuys")
public class LogInController
{
    
    @Autowired
    LogInServiceImpl logInService;
    
    static Logger LOGGER = LogManager.getLogger(LogInController.class);
    
    @RequestMapping("")
    public View home()
    {
        LOGGER.info("we made it home");
        return new InternalResourceView("index.html");
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String,User>> Authenticate(HttpServletRequest request)
    {
        LOGGER.info("autehntication");
        
        Map<String,User> response = new HashMap<>();
        
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        
        User u = logInService.checkAuthentication(userName, password);
        LOGGER.info(u);
        if(u == null)
        {
            response.put("ERROR", u);
        }
        else
        {
            response.put("SUCCESS", u);
        }
        LOGGER.info(response);
        return ResponseEntity.ok().header("log in", "Check").body(response);
    }
    
    @RequestMapping(value = "/admin/{adminID}")
    public View admin(@PathVariable int adminID, HttpSession session)
    {
        session.setAttribute("adminID", adminID);
        return new InternalResourceView("adminIndex.html");
    }
    
    @RequestMapping(value = "/customer/{customerID}")
    public View customer(@PathVariable int customerID, HttpSession session)
    {
        session.setAttribute("customerID", customerID);
        return new InternalResourceView("customerIndex.html");
    }
    
}
