package cn.batenglish.gallery.controller;

import cn.batenglish.gallery.entity.User;
import cn.batenglish.gallery.repository.UserRepository;
import cn.batenglish.gallery.service.EmailService;
import cn.batenglish.gallery.service.SessionService;
import cn.batenglish.gallery.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.NoSuchAlgorithmException;
import java.util.Random;


@Controller
@RequestMapping(path="/auth")
public class LoginController {
    private final UserService userService;
    private final EmailService emailService;

    private final SessionService sessionService;

    @Autowired
    public LoginController(SessionService sessionService,UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
        this.sessionService=sessionService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/sign_up")
    public String sign_up() {
        return "sign_up";
    }
    @GetMapping("/forgot_password")
    public String forgot_password() {
        return "forgot_password";
    }

    @PostMapping("/save_password")
    public String save_password(HttpServletRequest request,HttpServletResponse response, @RequestParam("password1") String password1) {

        if(!sessionService.validateSession(request)){
            throw new ResourceNotFoundException("Illegal request.");
        }

        User newUser= userService.getNewUser();
        newUser.setEmail(sessionService.getEmail());
        newUser.setPassword(password1);
        User addedUser = userService.addNewUser(newUser);
        if(addedUser!=null){
        Cookie cookieId = new Cookie("id", addedUser.getId().toString());
        cookieId.setMaxAge(-1);
        response.addCookie(cookieId);
        }
        Cookie cookieEmail = new Cookie("email", sessionService.getEmail());
        cookieEmail.setMaxAge(-1); // Cookie is valid for the duration of the session
        //session id is auto added
        response.addCookie(cookieEmail);

        return "welcome";
    }

    @PostMapping("/login_submit")
    public String login_submit(Model model, HttpServletRequest request,HttpServletResponse response, @RequestParam String email,@RequestParam String password) throws NoSuchAlgorithmException {
        //login logic
        System.out.println("login_submit");

       User user= userService.findByEmail(email);

       if(user!=null){
           if(!sessionService.validateSession(request)){
               throw new ResourceNotFoundException("Invalid session");
           }
          Boolean verified = userService.verifyPassword(password,user);
          if(verified){
              System.out.println("password verified");
              Cookie cookieEmail = new Cookie("email", email);
              Cookie cookieId = new Cookie("id", user.getId().toString());
              cookieId.setMaxAge(-1);
              cookieEmail.setMaxAge(-1); // Cookie is valid for the duration of the session
              //session id is auto added
              response.addCookie(cookieEmail);
              response.addCookie(cookieId);
              return "welcome";
          }else{
              model.addAttribute("status", "WRONG_PASSWORD");
              return "login";
          }
       }
        model.addAttribute("status", "NO_SUCH_USER");
        return "login";
    }
    @PostMapping("/send_email_code")
    public String send_email_code(Model model, @Email @RequestParam String email) {
        //check user email doesn't exist
        if(userService.findByEmail(email)!=null) {
            model.addAttribute("status", "EXIST");
        }else{
            Random random = new Random();
            String code =String.format("%06d", random.nextInt(1000000));
            sessionService.setCode(code);
            sessionService.setEmail(email);
            String content = "Your code is "+code;
            emailService.sendEmail(email,"Gallery sign up code",content);
            model.addAttribute("status", "SUCCESS");
        }
        model.addAttribute("email", email);
        return "sign_up";
    }
    @GetMapping("/verify_code")
    public String verify_code( @RequestParam String code, Model model) {

        if(sessionService.getCode().equals(code)){
            model.addAttribute("email", sessionService.getEmail());
            return "set_password";
        }
        return "verify_code_failed";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
