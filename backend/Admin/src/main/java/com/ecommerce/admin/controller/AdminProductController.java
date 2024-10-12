package com.windshop.phone.controller.admin;

import com.windshop.phone.controller.BaseController;
import com.windshop.phone.entity.Product;
import com.windshop.phone.model.AjaxResponse;
import com.windshop.phone.repository.ProductRepository;
import com.windshop.phone.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductController extends BaseController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    // ADD Sản phẩm
    @GetMapping(value = {"/add-product"})
    public String addProduct(final ModelMap model, final HttpServletRequest request,
                             @ModelAttribute("product") Product product)
            throws Exception {
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if (messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        model.addAttribute("product", new Product());
        return "admin/add-product";
    }

    @PostMapping(value = "/add-product")
    public String addproduct(@RequestParam("product_images") MultipartFile[] productImages, @ModelAttribute("product") Product product,
                             final ModelMap model)
            throws IOException {
        productService.save(productImages, product);
        model.addAttribute("product", product);
        return "redirect:/admin/add-product?message=success";
    }

    @GetMapping("/products")
    public String listProduct(final HttpServletRequest request, final ModelMap model,
                              @PathParam("page") Integer page) {
        int pageP = !ObjectUtils.isEmpty(page) ? page : 1;
        Pageable pageable = PageRequest.of(pageP - 1, 5);
        String search = request.getParameter("search");
        Page<Product> productList = productService.pageProduct(pageable);
        if (search != null) {
            List<Product> products = new ArrayList<Product>();
            for (Product product : productList.getContent()) {
                if (product.getTitle().toLowerCase().contains(search.toLowerCase()) || product.getId().toString().toLowerCase().contains(search.toLowerCase())
                        || product.getBrand().getName().toLowerCase().equals(search.toLowerCase())) {
                    products.add(product);
                }
            }
            model.addAttribute("products", products);
        } else {
            model.addAttribute("products", productList.getContent());
        }
        model.addAttribute("currentPage", pageP);
        model.addAttribute("total", productList.getTotalPages());
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if (messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Sửa thành công." +
                    "</div>");
        }
        return "admin/list-products";
    }

    //UPDATE Sản phẩm
    @GetMapping(value = "/update-product/{id}")
    public String updateProduct(@PathVariable("id") Integer id, final ModelMap model) {
        Product product = productService.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "admin/update-product";
    }

    @PostMapping(value = "/update-product")
    public String updateproduct(@RequestParam("product_images") MultipartFile[] productImages,
                                @ModelAttribute("product") Product product) throws IOException {
        productService.save(productImages, product);
        return "redirect:/admin/products?message=success";
    }

    @PostMapping(value = "/delete-product")
    public ResponseEntity<AjaxResponse> deleteProduct(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response,
                                                      @RequestBody Integer id) {
        Product product = productService.findById(id).orElse(null);
        product.setStatus(0);
        productRepository.save(product);
        return ResponseEntity.ok(new AjaxResponse(200, "Xoá thành công!"));
    }
}
