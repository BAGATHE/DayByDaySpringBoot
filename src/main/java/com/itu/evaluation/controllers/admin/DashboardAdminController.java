package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.Kpis;
import com.itu.evaluation.services.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class DashboardAdminController {
private final DashboardService dashboardService;

public DashboardAdminController(final DashboardService dashboardService) {
    this.dashboardService = dashboardService;
}



    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Récupérer le token de session
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }

        Map<String, Object> response = dashboardService.getKPIs(token);

        if (response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            if (data != null && data.containsKey("kpi")) {
                Map<String, Object> datas = (Map<String, Object>) data.get("kpi");
                System.out.println(datas.toString());
                Kpis kpis = new Kpis();
                kpis.setTotalOffers((int) datas.get("totalOffers"));
                kpis.setTotalInvoices((int)datas.get("totalInvoices"));
                kpis.setTotalInvoiceAmount(Double.parseDouble((String)datas.get("totalInvoiceAmount")));
                kpis.setTotalPayment(Double.parseDouble((String)datas.get("totalPayement")));
                model.addAttribute("kpis", kpis);
            }
        } else {
            model.addAttribute("error", "Impossible de charger les KPI.");
        }

        return "admin/dashboard";
    }
}
