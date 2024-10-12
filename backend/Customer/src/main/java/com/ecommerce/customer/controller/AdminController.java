package com.windshop.phone.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping(value = "/admin")
    public String indexAdmin() {
        return "admin/index";
    }

    @GetMapping(value = "/role-blocked")
    public String accessDenied() {
        return "admin/blocked-role";
    }

}
