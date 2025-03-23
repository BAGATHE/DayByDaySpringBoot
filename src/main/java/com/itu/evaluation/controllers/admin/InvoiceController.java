package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.InvoiceDto;
import com.itu.evaluation.services.InvoiceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itu.evaluation.utils.Utilitaire.formatNumber;

@Controller
public class InvoiceController {

    public final InvoiceService invoiceService;
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }



    @GetMapping("/admin/invoices")
    public String invoice(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }

        List<InvoiceDto> listInvoice = new ArrayList<>();
        Map<String, Object> response = invoiceService.getInvoices(token);

        if (response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");

            if (data != null && data.containsKey("invoices")) {
                List<Map<String, Object>> invoices = (List<Map<String, Object>>) data.get("invoices");

                for (Map<String, Object> invoice : invoices) {
                    InvoiceDto invoiceDto = new InvoiceDto();
                    invoiceDto.setId((int) invoice.get("id"));
                    invoiceDto.setStatus((String) invoice.get("status"));
                    invoiceDto.setSent_at((String) invoice.get("sent_at"));
                    invoiceDto.setDue_at((String) invoice.get("due_at"));
                    invoiceDto.setClient(invoice.get("client").toString());
                    invoiceDto.setSource(invoice.get("source").toString());
                    invoiceDto.setTotal_paid(formatNumber(invoice.get("total_paid")));
                    invoiceDto.setTotal_price(formatNumber(invoice.get("total_price")));
                    invoiceDto.setVat_total(formatNumber(invoice.get("vat_total")));
                    invoiceDto.setSub_total(formatNumber(invoice.get("sub_total")));
                    invoiceDto.setAmount_due(formatNumber(invoice.get("amount_due")));

                    listInvoice.add(invoiceDto);
                }

            }
        }
        model.addAttribute("invoices", listInvoice);
        return "admin/invoice/index";
    }





}
