package com.example.springstarbucks;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CashierCommand {
    private String milk ;
    private String drink ;
    private String size ;
    private String action;
    private String message;
    private String register;
}
