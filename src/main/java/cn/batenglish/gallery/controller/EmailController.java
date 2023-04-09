package cn.batenglish.gallery.controller;

import cn.batenglish.gallery.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
   @Autowired
   private EmailService emailService;
    @GetMapping("/send")
    public void sendEmail(  @RequestParam(value = "to") String to,
        @RequestParam(value = "subject") String subject,
        @RequestParam(value = "body") String body ) {
        emailService.sendEmail(to,subject,body);
    }
}
