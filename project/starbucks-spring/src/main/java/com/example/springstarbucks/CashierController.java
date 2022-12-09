package com.example.springstarbucks;

import com.example.springstarbucks.model.Order;
import com.example.springstarbucks.model.PingMvc;
import com.example.springstarbucks.model.Card;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class CashierController {

    private boolean ordercheck = false;

    @GetMapping
    public String getAction(@ModelAttribute("command") CashierCommand command, Model model) {
        return "cashier";
    }
    @PostMapping
    public String postAction(@ModelAttribute("command") CashierCommand command,
                             @RequestParam(value = "action", required = true) String action,
                             @RequestParam(value = "milk", required = false) String milk,
                             @RequestParam(value = "size", required = false) String size,
                             @RequestParam(value = "drink", required = false) String drink,
                             Errors errors, Model model, HttpServletRequest request) {
                             
        System.out.println( command );
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String resourceUrl = "" ;
        String message = "";

        headers.set("apikey", "Zkfokey2311");
        headers.add("size", size);
        headers.add("drink", drink);
        headers.add("milk", milk);

        if( action.equals("Get Order")) {
            message = "Get Order";
            resourceUrl ="http://34.29.34.25/api/order/register/5012349?apikey={apikey}" ;
            ResponseEntity<Order> orderResponse = restTemplate.getForEntity(resourceUrl, Order.class, "Zkfokey2311");
            Order orderMsg = orderResponse.getBody();
            System.out.println(orderMsg);
            try{
                ObjectMapper objectMapper = new ObjectMapper() ;
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderMsg);
                System.out.println(jsonString) ;
                message = "\n" + jsonString ;
            }
            catch (Exception e) {
                message = "\n" + "You already have an order!";
            }
        }

        if(action.equals("Place Order")) {
                message = "Place Order";
                resourceUrl = "http://34.29.34.25/api/order/register/5012349?apikey=Zkfokey2311";
                Order orderReq = new Order();
                orderReq.setDrink(drink);
                orderReq.setMilk(milk);
                orderReq.setSize(size);
                    
                    HttpEntity<Order> newOrderRequest = new HttpEntity<Order>(orderReq) ;
                    ResponseEntity<Order> newOrderResponse = restTemplate.postForEntity(resourceUrl, newOrderRequest, Order.class) ;
                    int code = newOrderResponse.getStatusCode().value();
                    
                    Order newOrder = newOrderResponse.getBody() ;
                    System.out.println( newOrder ) ;

                    try {
                    ObjectMapper objectMapper = new ObjectMapper() ;
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newOrder);
                    System.out.println( jsonString) ;
                    message = "\n" + jsonString ;
                    }
                    catch ( Exception e ) {}
            }
             
        if(action.equals("Clear Order"))
        {
                resourceUrl = "http://34.29.34.25/api/order/register/5012349?apikey={apikey}";
                restTemplate.delete(resourceUrl, "Zkfokey2311");
                message = "Active order cleared! Ready for new order.";
                ordercheck = false;
        }
        model.addAttribute("message", message);
        return "cashier";
    }


}