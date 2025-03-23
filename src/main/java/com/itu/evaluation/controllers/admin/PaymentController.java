package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.InvoiceDto;
import com.itu.evaluation.dto.PaymentDto;
import com.itu.evaluation.services.PaymentService;
import com.itu.evaluation.utils.Utilitaire;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itu.evaluation.utils.Utilitaire.formatNumber;

@Controller
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/admin/payments")
    public String payments(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }
        Map<String, Object> response = paymentService.getPayements(token);
        List<PaymentDto> listpayments =  new ArrayList<>();

        if(response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            if(data != null && data.containsKey("payments")) {
                List<Map<String, Object>> payments = (List<Map<String, Object>>) data.get("payments");
                  for(Map<String, Object> payment : payments) {
                      PaymentDto paymentDto = new PaymentDto();
                      paymentDto.setId((int) payment.get("id"));
                      paymentDto.setAmount(Utilitaire.formatNumber(payment.get("amount")));
                      paymentDto.setDescription((String) payment.get("description"));
                      paymentDto.setPaymentSource((String) payment.get("payment_source"));
                      paymentDto.setPaymentDate(Utilitaire.formatDeadline((String) payment.get("payment_date")));
                      paymentDto.setInvoiceId((int) payment.get("invoice_id"));
                      listpayments.add(paymentDto);
                  }
            }
        }
        model.addAttribute("payments", listpayments);
        return "admin/payment/index";
    }


}

