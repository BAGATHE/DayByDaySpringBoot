package com.itu.evaluation.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardAdminController {
@GetMapping("/dashboard")
    public String dashboard() {
    return "admin/dashboard";
}
}
