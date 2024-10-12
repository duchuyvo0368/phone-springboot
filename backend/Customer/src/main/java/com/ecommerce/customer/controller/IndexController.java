package com.windshop.phone.controller.user;

import com.windshop.phone.entity.Product;
import com.windshop.phone.entity.User;
import com.windshop.phone.service.IUserService;
import com.windshop.phone.service.impl.ProductServiceImpl;
import com.windshop.phone.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class IndexController {

    @Autowired
    private IUserService IUserService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping(value = "/home")
    public String index() {
        return "user/home";
    }

    @GetMapping(value = "/shop")
    public String shopPage(final HttpServletRequest request, final ModelMap model,
                           @PathParam("page") Integer page) {
        int pageP = !ObjectUtils.isEmpty(page)? page : 1;
        Pageable pageable = PageRequest.of(pageP-1, 12);
        Page<Product> productList = productService.pageProduct(pageable);
        model.addAttribute("products", productList.getContent());
        model.addAttribute("currentPage", pageP);
        model.addAttribute("total", productList.getTotalPages());
        return "user/shop";
    }

    @GetMapping(value = "/single-product")
    public String singleProduct(@PathParam("id") Integer id,
                                Model model) {
        Product product = productService.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "user/single-product";
    }

    @GetMapping(value = "/user/checkout")
    public String checkout() {
        return "user/checkout";
    }

    @GetMapping(value = "/sign-in")
    public String login(Model model, String error, String logout) {
        String message = (String) model.getAttribute("message");
        if (!ObjectUtils.isEmpty(message)){
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "");
        }
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("messageLogout", "You have been logged out successfully.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "user/sign-in";
        }

        return "redirect:/home";
    }

    @GetMapping(value = "/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userForm", new User());
        return "user/sign-up";
    }
    @PostMapping("/sign-up")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/sign-up";
        }

        userForm.setRole("USER");
        IUserService.save(userForm);
        redirectAttributes.addFlashAttribute("message", "success");

        return "redirect:/sign-in";
    }
}