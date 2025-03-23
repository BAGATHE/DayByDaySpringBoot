package com.itu.evaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private int id;
    private String client;
    private String leadSource;
    private String leadDescription;
    private String status;
    private String deadline;

}

