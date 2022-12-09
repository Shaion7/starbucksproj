package com.example.springstarbucks.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
        {
            "drink": "Caffe Latte",
            "milk": "Whole",
            "size": "Grande",
            "total": 3.91,
            "status": "Ready for Payment.",
            "register": "5012349"
        }
 */

@Data
@Getter
@Setter
public class Order {
    private String drink;
    private String milk;
    private String size;
    private double total;
    private String status;
    private String register;
    public String getDrink(){

        return drink;
    }
    public String getMilk()
    {
        return milk;
    }
    public String getSize()
    {
        return size;
    }
    public void setSize(String size1)
    {
        size = size1;
    }
    public void setMilk(String milk1)
    {
        milk = milk1;
    }
    public void setDrink(String drink1){
        
        drink = drink1;
    }
    public void setTotal(double total1)
    {
        total = total1;
    }

    public void setRegister(String reg1){
        register = reg1;
    }

    public void setStatus(String stat1)
    {
        status = stat1;
    }

}