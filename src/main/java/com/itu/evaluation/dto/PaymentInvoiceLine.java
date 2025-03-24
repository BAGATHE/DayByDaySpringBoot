package com.itu.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInvoiceLine {
    private double payment_amount;
    private double invoice_amount;
    private int mois;
    private int annee;
}
