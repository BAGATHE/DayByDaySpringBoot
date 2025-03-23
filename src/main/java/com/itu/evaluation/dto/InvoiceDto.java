package com.itu.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private int id;
    private String status;
    private String sent_at;
    private String due_at;
    private String client;
    private String source;
    private String total_paid;
    private String total_price;
    private String vat_total;
    private String sub_total;
    private String amount_due;
}
