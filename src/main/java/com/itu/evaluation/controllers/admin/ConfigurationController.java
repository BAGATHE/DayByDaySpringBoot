package com.itu.evaluation.controllers.admin;

import com.itu.evaluation.dto.RemiseInvoice;
import com.itu.evaluation.services.ImportService;
import com.itu.evaluation.services.RemiseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

@Controller
public class ConfigurationController {
private final RemiseService remiseService;
private final ImportService importService;

public ConfigurationController(RemiseService remiseService, ImportService importService0) {
    this.remiseService = remiseService;
    this.importService = importService0;
}

    @GetMapping("/admin/configuration/remise")
    public String remise(HttpSession session, Model model, @ModelAttribute("isSuccess") Object isSuccess, @ModelAttribute("message") Object message) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }

        Map<String, Object> response = remiseService.getRemise(token);
        if (response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            if (data != null && data.containsKey("remise")) {
                model.addAttribute("remise", data.get("remise"));
            }
        }

        // Ajouter les messages flash
        if (isSuccess != null) {
            model.addAttribute("isSuccess", isSuccess);
            model.addAttribute("message", message);
        }

        return "admin/configuration/remise";
    }

    @PostMapping("/admin/configuration/remise/update")
    public String updateRemise(HttpSession session, @RequestParam(name="remise") String remise, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/admin/login";
        }

        double valueremise = Double.parseDouble(remise);
        Map<String, Object> response = remiseService.UPDATEREMISE(token, valueremise);

        if (response != null && "success".equals(response.get("status"))) {
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            if (data != null && data.containsKey("isSuccess")) {
                redirectAttributes.addFlashAttribute("isSuccess", data.get("isSuccess"));
                redirectAttributes.addFlashAttribute("message", data.get("message").toString());
            }
        } else {
            redirectAttributes.addFlashAttribute("isSuccess", false);
            redirectAttributes.addFlashAttribute("message", "Échec de la mise à jour.");
        }

        return "redirect:/admin/configuration/remise";
    }



    @GetMapping("/admin/configuration/import")
    public String ImportEntityForm(){
        return "admin/entity/import";
    }

    @PostMapping("/admin/configuration/import")
    public String importEspace(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session, ImportService importService, Model model) throws IOException {
        try {
            String token = (String) session.getAttribute("token");
            if (token == null) {
                return "redirect:/admin/login";
            }
            Map<String, Object> response = remiseService.CLIENTIMPORT(token, file);
            if (response != null && response.containsKey("status") && "success".equals(response.get("status"))) {
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                String message = (String) data.get("message");
                redirectAttributes.addFlashAttribute("success", "Importation réussie !");
                model.addAttribute("message", message);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'importation : " + e.getMessage());
        }

        return "admin/entity/import";
    }

}
