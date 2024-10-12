package com.windshop.phone.controller.user;

import com.windshop.phone.entity.Contact;
import com.windshop.phone.repository.ContactRepository;
import com.windshop.phone.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class ContactController {

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private ContactRepository contactRepository;
    @GetMapping(value = "/contact")
    public String index()
            throws Exception {
        return "user/contact";
    }

    @PostMapping("/contact")
    public String saveContact(final HttpServletRequest request) {
        Contact con = new Contact();
        con.setName(request.getParameter("name"));
        con.setEmail(request.getParameter("email"));
        con.setMessage(request.getParameter("message"));
        con.setCreatedDate(LocalDateTime.now());
        contactRepository.save(con);

        sendEmailService.sendMail(request.getParameter("email"), "WindShop", "Dear "+request.getParameter("name")+",\nChúng tôi đã nhận được tin nhắn của bạn"
                + "Chúng tôi rất xin lỗi về sự bất tiện này. Chúng tôi cảm ơn bạn và sẽ đưa ra giải pháp về vấn đề của bạn!"
                + "\nBest guards");
        return "user/contact";
    }
}
