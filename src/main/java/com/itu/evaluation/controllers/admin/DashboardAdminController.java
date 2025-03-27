package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.Kpis;
import com.itu.evaluation.dto.NewClient;
import com.itu.evaluation.dto.PaymentInvoiceLine;
import com.itu.evaluation.dto.PaymentSource;
import com.itu.evaluation.services.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardAdminController {
private final DashboardService dashboardService;

public DashboardAdminController(final DashboardService dashboardService) {
    this.dashboardService = dashboardService;
}



    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model, @RequestParam(name = "year", defaultValue = "2025") String year) {
        // Récupérer le token de session
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }

        Map<String, Object> response = dashboardService.getKPIs(token,year);

        List<PaymentSource> listpayement = new ArrayList<>();
        if (response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            if (data != null && data.containsKey("kpi")) {
                Map<String, Object> datas = (Map<String, Object>) data.get("kpi");
                System.out.println(datas.toString());
                Kpis kpis = new Kpis();

                kpis.setTotalOffers((int) datas.get("totalOffers"));
                kpis.setTotalInvoices((int)datas.get("totalInvoices"));
                kpis.setTotalInvoiceAmount(Double.parseDouble(datas.get("totalInvoiceAmount").toString()));
                kpis.setTotalPayment(Integer.parseInt(datas.get("totalPayment").toString()));
                model.addAttribute("kpis", kpis);

                /*ssource payment**/
                List<Map<String, Object>> pie_source = (List<Map<String, Object>> ) data.get("paymentsource");
                for(Map<String, Object> pie_source_map : pie_source) {
                    PaymentSource paymentSource = new PaymentSource();
                    paymentSource.setPayment_source((String) pie_source_map.get("payment_source"));
                    paymentSource.setTotal_amount(Double.parseDouble((String)pie_source_map.get("total_amount")));
                    listpayement.add(paymentSource);
                }
                model.addAttribute("payementsources", listpayement);


                /**PayementINvoiceLine*/
                List<PaymentInvoiceLine> list_payementInvoice = new ArrayList<>();
                List<Map<String, Object>> list_payementInvoicelinesAmount = (List<Map<String, Object>> ) data.get("payementInvoicelinesAmount");
                for(Map<String, Object> payement_invoice_map : list_payementInvoicelinesAmount) {
                    PaymentInvoiceLine paymentInvoiceLine = new PaymentInvoiceLine();
                    paymentInvoiceLine.setInvoice_amount((int)payement_invoice_map.get("invoice_amount"));
                    paymentInvoiceLine.setPayment_amount((int)payement_invoice_map.get("payment_amount"));
                    paymentInvoiceLine.setMois((int) payement_invoice_map.get("mois"));
                    paymentInvoiceLine.setAnnee((int) payement_invoice_map.get("annee"));
                    list_payementInvoice.add(paymentInvoiceLine);
                }
                model.addAttribute("payementinvoicelines", list_payementInvoice);


                /**client nombre**/
                int count = 0;
                List<NewClient> list_clients = new ArrayList<>();
                List<Map<String, Object>> list_clientsMap = (List<Map<String, Object>>) data.get("clients");
                for(Map<String, Object> client_map : list_clientsMap) {
                    NewClient newClient = new NewClient();
                    int nombre  = (int)client_map.get("client_number");
                    count+=nombre;
                    newClient.setClient_number(nombre);
                    newClient.setMonth((int)client_map.get("month"));
                    newClient.setYear((int)client_map.get("year"));
                    list_clients.add(newClient);
                }
                model.addAttribute("clients", list_clients);
                model.addAttribute("nbclient", count);
            }

        } else {
            model.addAttribute("error", "Impossible de charger les KPI.");
        }

        return "admin/dashboard";
    }
}
