package com.example.pal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long id;
    private Double amount;
    private LocalDate paymentDate;
    private Long userId;

}
