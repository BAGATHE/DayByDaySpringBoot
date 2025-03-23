package com.itu.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private int id;
    private String amount;
    private String description;
    private String paymentSource;
    private String paymentDate;
    private int invoiceId;
}
