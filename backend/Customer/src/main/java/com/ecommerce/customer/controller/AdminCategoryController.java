package com.windshop.phone.controller.admin;

import com.windshop.phone.entity.Category;
import com.windshop.phone.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value = "/category")
    public String get(final ModelMap model, @PathParam("page") Integer page) {
        int pageP = !ObjectUtils.isEmpty(page) ? page : 1;
        Pageable pageable = PageRequest.of(pageP - 1, 5);
        Page<Category> categoryPage = categoryRepository.findAllByStatus(1, pageable);
        model.addAttribute("category", categoryPage.getContent());
        model.addAttribute("currentPage", pageP);
        model.addAttribute("total", categoryPage.getTotalPages());
        return "admin/category";
    }

    @GetMapping(value = "/add-category")
    public String add(final ModelMap model, final HttpServletRequest request) {
        model.addAttribute("category", new Category());
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if (messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        return "admin/add-category";
    }

    @PostMapping(value = "/add-category")
    public String addBrand(@ModelAttribute("category") Category category) {
        category.setCreatedDate(LocalDateTime.now());
        categoryRepository.save(category);
        return "redirect:/admin/add-category?message=success";
    }

    @PostMapping("/category")
    public String removeAd(@RequestParam("id") Integer id) {
        Category category = categoryRepository.getOne(id);
        category.setStatus(0);
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }
}
