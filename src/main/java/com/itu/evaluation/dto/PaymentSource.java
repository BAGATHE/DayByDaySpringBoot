package com.itu.evaluation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSource {
    private String payment_source;
    private double  total_amount;
}
