package com.itu.evaluation.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class EntityAdminController {

    @GetMapping("/entity/import")
    public String ImportEntityForm(){
        return "admin/entity/import";
    }

    @GetMapping("/entities")
    public String entityesList(){
        return "admin/entity/list";
    }
}
