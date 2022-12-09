package com.example.springstarbucks.model;

import lombok.Data;

@Data
public class Card {

    private String cardNumber ;
    private String cardCode ;
    private double balance ;
    private boolean activated ;
    private String status ;
    
}
