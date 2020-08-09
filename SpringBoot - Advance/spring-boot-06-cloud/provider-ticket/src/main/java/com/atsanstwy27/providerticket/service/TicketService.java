package com.atkjs927.providerticket.service;

import org.springframework.stereotype.Service;

@Service
public class TicketService {

    public  String getTicket() {
        System.out.println("8001");
        return "Well Done";
    }
}
