package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.InvoiceDto;
import com.itu.evaluation.dto.PaymentDto;
import com.itu.evaluation.services.PaymentService;
import com.itu.evaluation.utils.Utilitaire;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
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
    public String payments(Model model, HttpSession session,@ModelAttribute("message") Object message) {
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
        model.addAttribute("message", message);
        return "admin/payment/index";
    }

    @PostMapping("/admin/payments/edit")
    public String editPayment(@RequestParam("id") String id, @RequestParam("amount") String amount, RedirectAttributes redirectAttributes, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }
            double price = Double.parseDouble((String)amount);
            int id_payment = Integer.parseInt((String)id);

            Map<String,Object> response = paymentService.UPDATEPricePayement(token,id_payment,price);
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                redirectAttributes.addFlashAttribute("message", data.get("message").toString());
            return "redirect:/admin/payments";

    }

    @PostMapping("/admin/payments/delete/{id}")
    public String deletePayment(@PathVariable("id") String paymentId,HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }
        int id_payment = Integer.parseInt((String)paymentId);
        Map<String,Object> response = paymentService.DeletePayement(token,id_payment);
        return "redirect:/admin/payments"; // Redirection vers la liste des paiements
    }


}

