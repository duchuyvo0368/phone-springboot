package com.windshop.phone.controller.admin;

import com.windshop.phone.entity.User;
import com.windshop.phone.model.AjaxResponse;
import com.windshop.phone.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserManageController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user-management")
    public String listProduct(final HttpServletRequest request, final ModelMap model,
                              @PathParam("page") Integer page) {
        int pageP = !ObjectUtils.isEmpty(page) ? page : 1;
        Pageable pageable = PageRequest.of(pageP - 1, 5);
        String search = request.getParameter("search");
        Page<User> userPage = userService.findAll(pageable);
        if (search != null) {
            List<User> users = new ArrayList<>();
            for (User user : userPage.getContent()) {
                if (user.getEmail().toLowerCase().contains(search.toLowerCase()) || user.getId().toString().toLowerCase().contains(search.toLowerCase())
                        || user.getUsername().toLowerCase().contains(search.toLowerCase())) {
                    users.add(user);
                }
            }
            model.addAttribute("users", users);
        } else {
            model.addAttribute("users", userPage.getContent());
        }
        model.addAttribute("currentPage", pageP);
        model.addAttribute("total", userPage.getTotalPages());
        return "admin/account";
    }

    @PostMapping(value = "/delete-user")
    public ResponseEntity<AjaxResponse> deleteProduct(@RequestBody Integer id) {
        User user = userService.findById(id);
        user.setStatus(0);
        userService.save(user);
        return ResponseEntity.ok(new AjaxResponse(200, "Xoá thành công!"));
    }
}
