package com.windshop.phone.controller.admin;

import com.windshop.phone.entity.Brand;
import com.windshop.phone.repository.BrandRepository;
import com.windshop.phone.service.IBrandSerVice;
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
public class BrandController {

    @Autowired
    private IBrandSerVice brandService;

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/brands")
    public String get(final ModelMap model, @PathParam("page") Integer page) {
        int pageP = !ObjectUtils.isEmpty(page) ? page : 1;
        Pageable pageable = PageRequest.of(pageP - 1, 5);
        Page<Brand> brandPage = brandService.getBrands(pageable);
        model.addAttribute("brands", brandPage.getContent());
        model.addAttribute("currentPage", pageP);
        model.addAttribute("total", brandPage.getTotalPages());
        return "admin/brand";
    }

    @GetMapping("/add-brand")
    public String add(final ModelMap model, final HttpServletRequest request) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if (messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        return "admin/add-brand";
    }

    @PostMapping( "/add-brand")
    public String addBrand(@ModelAttribute("brand") Brand brand) {
        brand.setSeo("thuong-hieu-" + brand.getName());
        brand.setCreatedDate(LocalDateTime.now());
        brandService.save(brand);
        return "redirect:/admin/add-brand?message=success";
    }

    @PostMapping("/brands")
    public String removeAd(@PathParam("id") Integer id) {
        Brand brand = brandRepository.getOne(id);
        brand.setStatus(0);
        brandService.save(brand);
        return "redirect:/admin/brands";
    }
}
