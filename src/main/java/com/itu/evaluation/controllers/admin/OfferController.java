package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.OfferDto;
import com.itu.evaluation.services.OfferService;
import com.itu.evaluation.utils.Utilitaire;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OfferController {
private final OfferService offerService;

public OfferController(OfferService offerService) {
    this.offerService = offerService;
}

    @GetMapping("/admin/offers")
    public String offers(Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }

        Map<String, Object> response = offerService.getOffers(token);
        List<OfferDto> listoffers = new ArrayList<>();
        Map<String, Integer> statusCount = new HashMap<>();

        if (response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");

            if (data != null && data.containsKey("offers")) {
                List<Map<String, Object>> offers = (List<Map<String, Object>>) data.get("offers");
                for (Map<String, Object> offer : offers) {
                    OfferDto offerDto = new OfferDto();
                    offerDto.setId((int) offer.get("id"));

                    Map<String, Object> client = (Map<String, Object>) offer.get("client");
                    Map<String, Object> source = (Map<String, Object>) offer.get("source");

                    offerDto.setLeadSource((String) source.get("title"));
                    offerDto.setLeadDescription((String) source.get("description"));
                    offerDto.setDeadline(Utilitaire.formatDeadline((String) source.get("deadline")));
                    offerDto.setClient(client.get("company_name").toString());
                    offerDto.setStatus((String) offer.get("status"));

                    // Comptabilisation du statut
                    statusCount.put(offerDto.getStatus(), statusCount.getOrDefault(offerDto.getStatus(), 0) + 1);

                    listoffers.add(offerDto);
                }
            }
        }

        model.addAttribute("offers", listoffers);
        model.addAttribute("statusCount", statusCount);

        return "admin/offer/index";
    }


}
