package com.windshop.phone.controller.admin;

import com.windshop.phone.controller.BaseController;
import com.windshop.phone.entity.Brand;
import com.windshop.phone.entity.Contact;
import com.windshop.phone.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@Controller
public class AdminEmailController extends BaseController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/admin/email")
    public String index(final ModelMap model, @PathParam("page") Integer page) {
        int pageP = !ObjectUtils.isEmpty(page) ? page : 1;
        Pageable pageable = PageRequest.of(pageP - 1, 10);
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        model.addAttribute("brands", contactPage.getContent());
        model.addAttribute("currentPage", pageP);
        model.addAttribute("total", contactPage.getTotalPages());
        model.addAttribute("contacts", contactRepository.findAll());
        return "admin/email";
    }

    @PostMapping("/admin/email")
    public String indexz(@PathParam("id") Integer id) {
        contactRepository.deleteById(id);
        return "redirect:/admin/email";
    }
}
